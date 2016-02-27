package br.com.caelum.cadastro.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.caelum.cadastro.ProvasActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Prova;

/**
 * Created by thiago on 2/27/16.
 */
public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutProvas = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        Prova prova1 = new Prova("20/06/2015", "Matemática");
        prova1.setTopicos(Arrays.asList("Álgebra linear", "Cálculo", "Estatística"));

        Prova prova2 = new Prova("25/07/2015", "Português");
        prova2.setTopicos(Arrays.asList("Complemento nominal", "Orações subordinadas", "Análise sintática"));

        List<Prova> provas = Arrays.asList(prova1, prova2);

        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);
        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(), android.R.layout.simple_list_item_1, provas));

        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Prova selecionada = (Prova) adapter.getItemAtPosition(posicao);

                ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
                calendarioProvas.selecionaProva(selecionada);
            }
        });

        return layoutProvas;
    }
}
