package com.realkarim.apps.pinsvalley;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 1/28/17.
 */

interface PinsContract {
    interface  View{
        void receiveListUpdate(ArrayList arrayList);
        void showMessage(String message);
    }

    interface Presenter{
        void updateList();
    }
}
