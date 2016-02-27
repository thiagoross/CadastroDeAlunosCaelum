package br.com.caelum.cadastro.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by thiago on 2/20/16.
 */
public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private final Context context;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(Object... params) {
        AlunoDAO dao = new AlunoDAO(this.context);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String json = new AlunoConverter().toJSON(alunos);

        WebClient client = new WebClient();
        String resposta = null;
        try {
            resposta = client.post(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        super.onPostExecute(resposta);

        Toast.makeText(this.context, resposta, Toast.LENGTH_LONG).show();
    }
}
