package com.acpi.mls.missionlunarspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acpi.mls.missionlunarspace.DAO.DAOCheckPartieDemarree;
import com.acpi.mls.missionlunarspace.DAO.DAOEtudiant;

public class EtudiantActivity extends AppCompatActivity {

    private String nomEtu;
    private String classeEtu;
    private String anneeEtu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
    }

    public void onClikButtonContinuer(View view) throws InterruptedException {
        EditText etuNom = findViewById(R.id.textViewNomEtu);
        String nom = etuNom.getText().toString();

        EditText etuClasse = findViewById(R.id.textViewClasseEtu);
        String classe = etuClasse.getText().toString();

        EditText etuAnnee = findViewById(R.id.textViewAnneeEtu);
        String annee = etuAnnee.getText().toString();

        if (verifText(nom, classe, annee)) {
            this.nomEtu = nom;
            this.classeEtu = classe;
            this.anneeEtu = annee;
            new DAOEtudiant(EtudiantActivity.this).execute("getIdClasse","existClasse",this.classeEtu,annee);
        } else {
            Toast.makeText(this, "Vous devez renseigner tous les champs !", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCLickTempo(View view) {
        Intent intent = new Intent(this, ChoixPersoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("NomEtu", nomEtu);
        bundle.putString("ClasseEtu", classeEtu);
        bundle.putString("AnneeEtu",anneeEtu);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private boolean verifText(String nom, String classe, String annee) {
        return !classe.equals("") && !nom.equals("") && !annee.equals("");
    }

    public void existeClasse(String id) {
        if (!id.equals("")) {
            //TODO Enlever la ligne pour recommencer l'ajout d'étudiant et enlever l'appel du set qui sera fait automatiquement dans le DAO
            //new DAOEtudiant(EtudiantActivity.this).execute("createEtudiant","setIdEtu",this.nomEtu);
            Constantes.idEtudiant = 1;

            setContentView(R.layout.content_etudiant_attente);
            Button boutonSuiveAttente = (Button) findViewById(R.id.buttonTempoAttente);
            //TODO changer par boutonSuiveAttente.setVisibility(View.INVISIBLE);
            boutonSuiveAttente.setVisibility(View.VISIBLE);
            new DAOCheckPartieDemarree(boutonSuiveAttente, Integer.parseInt(id)).execute();
        }
        else
            Toast.makeText(this, "La classe n'existe pas.", Toast.LENGTH_SHORT).show();
    }
}
