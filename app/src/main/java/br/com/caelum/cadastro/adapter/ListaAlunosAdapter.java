package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by thiago on 2/20/16.
 */
public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(List<Aluno> alunos, Activity activity) {
        this.alunos = alunos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        Aluno aluno = (Aluno)this.getItem(position);
        return aluno.getId();
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        Aluno aluno = (Aluno)getItem(position);

        LayoutInflater inflater = LayoutInflater.from(this.activity);
        View view = inflater.inflate(R.layout.item, parent, false);

        ImageView imagem = (ImageView)view.findViewById(R.id.item_foto);

        Bitmap bm;
        if (aluno.getFoto() != null) {
            bm = BitmapFactory.decodeFile(aluno.getFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.person);
        }
        bm = Bitmap.createScaledBitmap(bm, 100, 100, true);

        imagem.setImageBitmap(bm);

        TextView nome = (TextView)view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        return view;
    }
}
