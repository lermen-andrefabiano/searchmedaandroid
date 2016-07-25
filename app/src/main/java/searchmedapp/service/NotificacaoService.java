package searchmedapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import android.R;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import searchmedapp.ConsultaAbertaPacienteActivity;
import searchmedapp.MainActivity;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.rest.ConsultaREST;

/**
 * Created by Andre on 24/07/2016.
 */
public class NotificacaoService extends Service {

    private static final String TAG = "NotificacaoService";

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        createNotification();
    }

    public void createNotification() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SearchMedPref", Context.MODE_PRIVATE);
        String user = pref.getString("key_user_id", "");
        List<ConsultaDTO> consultasNotification = null;

        try {
            ConsultaREST rest = new ConsultaREST();
            consultasNotification = rest.consultasEmAndamento(Long.valueOf(user));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (consultasNotification != null) {
            for (int i = 0; i < consultasNotification.size(); i++) {
                ConsultaDTO c = consultasNotification.get(i);
                Intent intent = new Intent(getApplicationContext(), ConsultaAbertaPacienteActivity.class);
                Gson gson = new Gson();
                String jsonConsultaSel = gson.toJson(c);
                intent.putExtra("jsonConsultaSel", jsonConsultaSel);

                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                Notification noti = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Consulta agendada")
                        .setContentText(c.getMedico().getMedicoNome() + " " + c.getEspecialidade().getDescricao())
                        .setSmallIcon(searchmedapp.R.drawable.ic_event_black_24dp)
                        .setContentIntent(pIntent)
                        .addAction(searchmedapp.R.drawable.ic_location_on_black_24dp, "Localização", pIntent).build();
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                // hide the notification after its selected
                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(i, noti);
            }
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
