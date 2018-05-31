package ua.in.zeusapps.mediar.utilities;

import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.models.Card;

/**
 * Created by Artem on 05.03.2018.
 */

public class Utils {
    public static int getAnimalTypeResource(Card card){
        switch (card.getKindID()){
            case "crocodile":
                return R.drawable.card_crocodile;

            case "elephant":
                return R.drawable.card_elephant;

            case "frog":
                return R.drawable.card_frog;

            case "giraffe":
                return R.drawable.card_giraffe;

            /*case "hippo":
                return ;*/ /// мав би бути ще й цей

            case "buffalo":
                return R.drawable.card_buffalo;

            case "leopard":
                return R.drawable.card_leo;

            case "lion":
                return R.drawable.card_lion;

            case "ostrich":
                return R.drawable.card_ostrich;

            case "owl":
                return R.drawable.card_owl;

            case "rhino":
                return R.drawable.card_rhino;

            case "wild_dog":
                return R.drawable.card_wilddog;
        }

        return R.drawable.card_elephant; /// кал
    }
}

