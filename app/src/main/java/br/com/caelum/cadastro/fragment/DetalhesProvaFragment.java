package br.com.caelum.cadastro.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.caelum.cadastro.R;

/**
 * Created by thiago on 2/27/16.
 */
public class DetalhesProvaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);
        return layout;
    }
}
