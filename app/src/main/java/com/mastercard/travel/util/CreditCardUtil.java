package com.mastercard.travel.util;

import com.anypresence.masterpass_android_library.MPManager;
import com.mastercard.travel.R;

/**
 * Created by diego.rotondale on 26/02/2015.
 */
public class CreditCardUtil {

    public static int cardImageForCardType(String cardType, String lastFour) {

        // We can't just do a random card because when the view
        // in the slider is recycled and/or reloaded we will
        // get a new image.

        // So we are going to use the last four as a 'seed' and
        // decide which image to use based on whether it is odd
        // or even. That way, we get the same image each time.
        Integer lastFourValue = Integer.valueOf(lastFour);
        int index = lastFourValue % 2;
        if (cardType != null) {
            if (cardType.equals(MPManager.CARD_TYPE_AMEX)) {
                switch (index) {
                    case 0:
                        return R.drawable.amex_black;
                    case 1:
                        return R.drawable.amex_blue;
                }
            }
            if (cardType.equals(MPManager.CARD_TYPE_DISCOVER)) {
                switch (index) {
                    case 0:
                        return R.drawable.discover_grey;
                    case 1:
                        return R.drawable.discover_orange;
                }
            }
            if (cardType.equals(MPManager.CARD_TYPE_MASTERCARD)) {
                switch (index) {
                    case 0:
                        return R.drawable.mastercard_blue;
                    case 1:
                        return R.drawable.mastercard_green;
                }
            }
            if (cardType.equals(MPManager.CARD_TYPE_VISA)) {
                switch (index) {
                    case 0:
                        return R.drawable.visa_blue;
                    case 1:
                        return R.drawable.visa_red;
                }
            }
            if (cardType.equals(MPManager.CARD_TYPE_MAESTRO)) {
                switch (index) {
                    case 0:
                        return R.drawable.maestro_blue;
                    case 1:
                        return R.drawable.maestro_yellow;
                }
            }
        }
        // Some Generic Card
        index = lastFourValue % 3;
        switch (index) {
            case 0:
                return R.drawable.generic_card_blue;
            case 1:
                return R.drawable.generic_card_green;
            case 2:
                return R.drawable.generic_card_orange;
        }
        //Never must come here
        return R.drawable.blue_card;
    }
}
