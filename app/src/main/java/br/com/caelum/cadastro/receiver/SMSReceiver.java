package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;

/**
 * Created by thiago on 2/20/16.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[])intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[])pdus[0];

        SmsMessage sms = SmsMessage.createFromPdu(pdu);

        AlunoDAO dao = new AlunoDAO(context);

        if (dao.ehAluno(sms.getDisplayOriginatingAddress())) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
            Toast.makeText(context, "Chegou um SMS!!!", Toast.LENGTH_LONG).show();
        }
    }
}
