package searchmedapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Andre on 24/07/2016.
 */
public class NotificacaoStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificacaoService.class);
        context.startService(service);
    }

}
