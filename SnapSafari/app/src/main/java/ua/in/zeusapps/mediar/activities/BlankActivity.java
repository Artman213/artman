package ua.in.zeusapps.mediar.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.adapters.RecyclerDistanceAdapter;
import ua.in.zeusapps.mediar.common.Layout;
import ua.in.zeusapps.mediar.controls.ARCamera;
import ua.in.zeusapps.mediar.controls.AROverlayView;
import ua.in.zeusapps.mediar.controls.AnimationsContainer;
import ua.in.zeusapps.mediar.controls.MenuMap;
import ua.in.zeusapps.mediar.models.Event;
import ua.in.zeusapps.mediar.models.EventRequest;


@Layout(R.layout.activity_menu_dark)
public class BlankActivity extends ActivityBase implements
        SensorEventListener,
        LocationListener {

    final static String TAG = "BlankActivity";

    private SurfaceView surfaceView;
    private FrameLayout cameraContainerLayout;
    private RelativeLayout catchContainerLayout;
    private AROverlayView arOverlayView;
    private Camera camera;
    private ARCamera arCamera;
    float distance = 0;
//    private TextView tvCurrentLocation;
//    private TextView arPointLocationTextView;
    private HashMap<Integer, ImageView> animatedViews;
    private SensorManager sensorManager;
    private final static int REQUEST_CAMERA_PERMISSIONS_CODE = 11;
    private final static int REQUEST_LOCATION_PERMISSIONS_CODE = 0;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    private LocationManager locationManager;
    private Location location;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean locationServiceAvailable;
    private boolean isARPointsUpdated;
    private RecyclerDistanceAdapter mAdapter;
    private List<String> distances;
    private boolean startInitialization;

    private ArrayList<Event> _events;
    private List<AnimationsContainer> _containers;
//    private Card _card;

//    private boolean firstrun = true;

    @BindView(R.id.activity_menu_map)
    MenuMap _menu;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _events = new ArrayList<>();
        isARPointsUpdated = false;
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
//        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        cameraContainerLayout = (FrameLayout) findViewById(R.id.camera_container_layout);
        catchContainerLayout = (RelativeLayout) findViewById(R.id.catch_container_layout);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        arOverlayView = new AROverlayView(this);
        startInitialization = true;

        distances = updateDistanceList();

        initRecycler();

    }

    @OnClick({R.id.snap_animal})
    public void snapOnClick(){
        Intent intent;
        Event nearestEvent = getNearestEvent();
        if (nearestEvent != null) {
            intent = new Intent(this, SnapActivity.class);
            intent.putExtra(SnapActivity.CARD_EXTRA, nearestEvent.getCard());
            startActivity(intent);
        }
    }


    @OnClick({R.id.activity_menu_bottom_menu, R.id.activity_menu_map})
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        switch (id) {

            case R.id.menu_hamburger:
                finish();
                break;

            case R.id.bottom_menu_left:
                intent = new Intent(this, SnapCardsActivity.class);
                startActivity(intent);
                break;

            case R.id.bottom_menu_center:
                this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!isGPSEnabled) {
                    Toast.makeText(this, "GPS is disable.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("events", _events);
                bundle.putParcelable("location", location);

                intent = new Intent(BlankActivity.this, MapActivity.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
                break;

            case R.id.bottom_menu_right:

                break;

            case R.id.menu_map:

                break;
        }
    }

    private void initRecycler() {
        mAdapter = new RecyclerDistanceAdapter(distances, _events, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_animals_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private List<String> updateDistanceList() {
        List<String> distances = new ArrayList<>();
        for (Event event : _events) {
            distances.add(String.format(
                                    getResources().getString(R.string.distance_model)
                                    , location.distanceTo(event.getArPoint().getLocation())));
        }

        return distances;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestLocationPermission();
        requestCameraPermission();
        registerSensors();
        initAROverlayView();
    }

    @Override
    public void onPause() {
        releaseCamera();
        super.onPause();
    }

    public void moveAnimationTo(float x, float y, float scale, int eventID, float distance) {
//        for (Map.Entry<Integer, ImageView> entry : animatedViews.entrySet()) {
//            Integer key = entry.getKey();
//            ImageView value = entry.getValue();
//            Log.d("artman", "event id: " + key + ", visibility value: " + String.valueOf(value.getVisibility()));
//        }
        if (startInitialization){
            initializeAnimation();
            startInitialization = false;
        }
        ImageView animatedView = animatedViews.get(eventID);
        if (animatedView == null) {
            return;
//            animatedView = animatedViews.get("elephant");
        }
        if (distance > 50.0) {
            animatedView.setVisibility(View.GONE);

        } else {
            animatedView.setVisibility(View.VISIBLE);
            animatedView.setTranslationX(x);
            animatedView.setTranslationY(y);
            animatedView.setScaleX(scale);
            animatedView.setScaleY(scale);
        }
        FrameLayout mainLayout = (FrameLayout) findViewById(R.id.camera_container_layout);

        Log.d("artman","animatedView moveAnimationTo => " +String.valueOf(animatedView.getVisibility()));
        Log.d("artman","mainLayout moveAnimationTo => " +String.valueOf(mainLayout.getVisibility()));


    }

    public void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSIONS_CODE);
        } else {
            initARCameraView();
        }
    }

    public void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS_CODE);
        } else {
            initLocationService();
        }
    }

    public void initAROverlayView() {
        if (arOverlayView.getParent() != null) {
            ((ViewGroup) arOverlayView.getParent()).removeView(arOverlayView);
        }
        cameraContainerLayout.addView(arOverlayView, 1);
    }

    public void initARCameraView() {
        reloadSurfaceView();

        if (arCamera == null) {
            arCamera = new ARCamera(this, surfaceView);
        }
        if (arCamera.getParent() != null) {
            ((ViewGroup) arCamera.getParent()).removeView(arCamera);
        }
        cameraContainerLayout.addView(arCamera);
        arCamera.setKeepScreenOn(true);
        initCamera();
    }

    private void initCamera() {
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open();
                camera.startPreview();
                arCamera.setCamera(camera);
            } catch (RuntimeException ex) {
                Toast.makeText(this, "Camera not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void reloadSurfaceView() {
        if (surfaceView.getParent() != null) {
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        }

        cameraContainerLayout.addView(surfaceView);
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            arCamera.setCamera(null);
            camera.release();
            camera = null;
        }
    }

    private void registerSensors() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrixFromVector = new float[16];
            float[] projectionMatrix = new float[16];
            float[] rotatedProjectionMatrix = new float[16];

            SensorManager.getRotationMatrixFromVector(rotationMatrixFromVector, sensorEvent.values);

            if (arCamera != null) {
                projectionMatrix = arCamera.getProjectionMatrix();
            }

            Matrix.multiplyMM(rotatedProjectionMatrix, 0, projectionMatrix, 0, rotationMatrixFromVector, 0);
            this.arOverlayView.updateRotatedProjectionMatrix(rotatedProjectionMatrix);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //do nothing
    }

    private void initLocationService() {

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            this.locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            // Get GPS and network status
            if (locationManager != null) {
                this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }

            if (!isNetworkEnabled && !isGPSEnabled) {
                // cannot get location
                this.locationServiceAvailable = false;
            }

            this.locationServiceAvailable = true;

            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    Log.d("artman","location NETWORK_PROVIDER -> " + location);
//                    location = locationManager.getLastKnownLocation(locationManager
//                            .getBestProvider(new Criteria(), false));
                    if (location != null) {
                        updateLatestLocation();
                    }
                }
            }

            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    Log.d("artman","location GPS_PROVIDER -> " + location);
//                    location = locationManager.getLastKnownLocation(locationManager
//                            .getBestProvider(new Criteria(), false));
                    //SK: customer location
//                    location.setLatitude(-1.28611111);
//                    location.setLongitude(36.77944444);
                    //dstudio
//                    this.location.setLatitude(-1.28611111);
//                    this.location.setLongitude(36.7794444);
                    if (location != null) {
                        updateLatestLocation();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

        }
    }

    private void updateLatestLocation() {
        if (arOverlayView != null) {
            arOverlayView.updateCurrentLocation(location);
        }
        updateCatchButton();
    }

    private void updateCatchButton() {
//        int res = 0;

        if (getNearestEvent() == null) {
            catchContainerLayout.setVisibility(View.GONE);
        } else {
            catchContainerLayout.setVisibility(View.VISIBLE);
        }

//        catchButton.setImageDrawable(ContextCompat.getDrawable(this.getBaseContext(), res));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
//        Log.d("artman","onLocationChanged -> " +location);

        //SK: customer location
//        this.location.setLatitude(-1.28611111);
//        this.location.setLongitude(36.77944444);
        //dstudio
//        this.location.setLatitude(-1.28611111);
//        this.location.setLongitude(36.7794444);
        if (this.location != null && !isARPointsUpdated) {
            updateCards(location);
            isARPointsUpdated = true;
        }

        distances = updateDistanceList();
        if (mAdapter.getAnimals().size() != _events.size()) {
            mAdapter.setAnimals(_events);
        }
        mAdapter.updateList(distances);

        updateLatestLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void updateCards(Location location) {

        EventRequest request = new EventRequest((float) (location.getLatitude()), (float) (location.getLongitude()), 10);
        getApp().getService()
                .getEvents(getToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(@NonNull List<Event> events) throws Exception {

                        _events = (ArrayList<Event>) events;
                        arOverlayView.events = events;
                        isARPointsUpdated = true;

                        _containers = new ArrayList<>();

                        if (events.size() > 0) {
                            runOnUiThread(new Runnable() {
                                public void run() {
//                                    initializeAnimation();
                                }
                            });
                        }

//                        ArrayList<CardAnimation> cardAnimations = new ArrayList<CardAnimation>();
//                        for (final Event event: events) {
//                            String url = "cards/get_animal_animation/" + event.getCard().getKindID() + "&" + event.getCard().getElement()+"/";
//
//                            getApp().getService().getAnimation(getToken(), url)
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribe(new Consumer<CardAnimation>() {
//                                        @Override
//                                        public void accept(@NonNull CardAnimation cardAnimation) throws Exception {
//                                            event.setAnimationURL(cardAnimation.getAnimationURL());
//                                            Uri imageUri = Uri.parse(getApp().BASE_URL + cardAnimation.getAnimationURL());
//                                            downloadData(imageUri);
//                                        }
//                                    }, new Consumer<Throwable>() {
//                                        @Override
//                                        public void accept(@NonNull Throwable throwable) throws Exception {
//                                            Log.d(ElephantActivity.class.getSimpleName(), throwable.getMessage());
//                                        }
//                                    });
//                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(ElephantActivity.class.getSimpleName(), throwable.getMessage());
                    }
                });

    }

    private void initializeAnimation() {


        animatedViews = new HashMap<Integer, ImageView>();

        FrameLayout mainLayout = (FrameLayout) findViewById(R.id.camera_container_layout);
        LayoutInflater inflater = getLayoutInflater();

        for (Event event : _events) {
            String kind = event.getCard().getKindID();
            if (animatedViews.get(kind) != null) {
                continue;
            }
            View animationContainer = (View) inflater.inflate(R.layout.animation_container, mainLayout, false);
            mainLayout.addView(animationContainer);
            ImageView animalsImageView = (ImageView) animationContainer.findViewById(R.id.animation_frame);
            addDrawable(animalsImageView, kind);
            animalsImageView.setVisibility(View.GONE);
            animatedViews.put(event.getId(), animalsImageView);
            Log.d("artman","animalsImageView  initializeAnimation => " +String.valueOf(animalsImageView.getVisibility()));
            Log.d("artman","animationContainer  initializeAnimation=> " +String.valueOf(animationContainer.getVisibility()));
            Log.d("artman","mainLayout  initializeAnimation=> " +String.valueOf(mainLayout.getVisibility()));


        }
    }


    private void addDrawable(ImageView view, String kind) {
        String fileName = null;
        if (kind.equals("buffalo")) {
            fileName = "al_buffalo";
        } else if (kind.equals("crocodile")) {
            fileName = "al_crocodile";
        } else if (kind.equals("elephant")) {
            fileName = "al_elephant";
        } else if (kind.equals("frog")) {
            fileName = "al_frog";
        } else if (kind.equals("giraffe")) {
//            fileName  = "al_giraffe";
        } else if (kind.equals("hippo")) {
//            fileName  = "al_hippo";
        } else if (kind.equals("leopard")) {
            fileName.equals("al_leopard");
        } else if (kind.equals("lion")) {
//            fileName  = "al_lion";
        } else if (kind.equals("owl")) {
//            fileName  = "al_owl";
        } else if (kind.equals("rhino")) {
            fileName = "al_rhino";
        } else if (kind.equals("wild_dog")) {
            fileName = "al_wild_dog";
        }

        if (fileName == null) {
            return;
        }

        int fileID = getResources().getIdentifier(kind, "array", getPackageName());
        String[] frameNames = getResources().getStringArray(fileID);
        int count = frameNames.length;
        int[] frameIDs = new int[frameNames.length];

        for (int i = 0; i < count; i++) {
            frameIDs[i] = getResources().getIdentifier(frameNames[i], "drawable", getPackageName());
        }
        AnimationsContainer container = new AnimationsContainer(view);
        container.addAllFrames(frameIDs, 50);
        container.start();
        _containers.add(container);
    }

    public void setAnimalVisible(int status) {
        surfaceView.setVisibility(status);
    }

    private Event getNearestEvent() {
        Event nearestEvent = null;
        if (_events != null) {
            for (Event event : _events) {
                if (this.location.distanceTo(event.getArPoint().getLocation()) <= 10) {
                    nearestEvent = event;
                    break;
                }
            }
        }
        return nearestEvent;
    }

//    private long downloadData (Uri uri) {
//
//        long downloadReference;
//
//        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setTitle("Data Download");
//        request.setDescription("Android Data download using DownloadManager.");
//        request.setDestinationInExternalFilesDir(ARActivity.this, Environment.DIRECTORY_DOWNLOADS,"AndroidTutorialPoint.mp3");
//        downloadReference = downloadManager.enqueue(request);
//
//        return downloadReference;
//    }

}
