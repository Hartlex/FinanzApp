package com.example.finanzapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.finanzapp.Categories.Category;
import com.example.finanzapp.Categories.CategoryManager;
import com.example.finanzapp.Categories.CategoryType;
import com.example.finanzapp.Helpers.Toaster;
import com.example.finanzapp.database.Database;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private Button DeleteDatabase,addExpenseCatBtn,addRevenueCatBtn,removeExpenseCatBtn,removeRevenueCatBtn;
    private EditText newExpenseText,newRevenueText;
    private Spinner expenseCatSpinner,revenueCatSpinner;
    // TODO: Retrieve the objects from the other spinner element
    ArrayList list=new ArrayList(Arrays.asList(  ));

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        DeleteDatabase = (Button) v.findViewById(R.id.btn_delete);
        DeleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetDatabase();
            }
        });

        newExpenseText = (EditText) v.findViewById(R.id.expenseEditText);
        newRevenueText = (EditText) v.findViewById(R.id.revenueEditText);
        addExpenseCatBtn =  (Button) v.findViewById(R.id.addExpenseCatBtn);
        addExpenseCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategory(CategoryType.EXPENSE);
            }
        });
        addRevenueCatBtn = (Button) v.findViewById(R.id.addRevenueCatBtn);
        addRevenueCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategory(CategoryType.REVENUE);
            }
        });
        removeExpenseCatBtn = v.findViewById(R.id.removeExpenseCatBtn);
        removeExpenseCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveCategory(CategoryType.EXPENSE);
            }
        });
        removeRevenueCatBtn = v.findViewById(R.id.removeRevenueCatBtn);
        removeRevenueCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveCategory(CategoryType.REVENUE);
            }
        });
        expenseCatSpinner = (Spinner) v.findViewById(R.id.expenseCatSpinner);
        revenueCatSpinner = (Spinner) v.findViewById(R.id.revenueCatSpinner);



        LoadSpinnerData(CategoryType.EXPENSE);
        LoadSpinnerData(CategoryType.REVENUE);


        return v;
    }

        /**
        * Reset the Database
        *
        * @author Di Seri.F
        * */
        private void ResetDatabase() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Haushaltsbuch");
            builder.setMessage("Sind Sie sicher, dass Sie alle Datensätze löschen möchten?");
            builder.setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Toaster.toast("Alle Datensätze wurden gelöscht!", getContext());
                    Database.ClearDatabase();
                    CategoryManager.Initialize();
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                }
            });
            builder.create();
            builder.show();
        }

    /**
     * creates and adds a Category
     * @param type the type of category that should be created
     */
    public void AddCategory(CategoryType type){
            String catName = newExpenseText.getText().toString();
            if(type == CategoryType.REVENUE)
                catName =  newRevenueText.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Kategorie erstellen");
            builder.setMessage("Sind Sie sicher, dass Sie die Kategorie "+catName+" erstellen wollen?");
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String catName = newExpenseText.getText().toString();
                    if(type == CategoryType.REVENUE)
                        catName =  newRevenueText.getText().toString();
                    CategoryManager.AddCategory(catName,type);
                    LoadSpinnerData(type);
                    Toaster.toast("Kategorie hinzugefügt!", getContext());
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                }
            });
            builder.create();
            builder.show();
        }

    /**
     * removes and deletes a Category
     * @param type the type of category that should be deleted
     */
        private void RemoveCategory(CategoryType type){
            String catName = expenseCatSpinner.getSelectedItem().toString();
            if(type == CategoryType.REVENUE)
                catName =  revenueCatSpinner.getSelectedItem().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Kategorie löschen");
            builder.setMessage("Sind Sie sicher, dass Sie die Kategorie "+catName+" löschen wollen?");
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String catName = expenseCatSpinner.getSelectedItem().toString();
                    if(type == CategoryType.REVENUE)
                        catName =  revenueCatSpinner.getSelectedItem().toString();
                    Category cat = CategoryManager.GetCategory(catName);
                    if(cat ==null) return;
                    CategoryManager.RemoveCategory(cat);
                    LoadSpinnerData(type);
                    Toaster.toast("Kategorie gelöscht!", getContext());
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                }
            });
            builder.create();
            builder.show();
        }

    /**
     * Loads/Reloads the Spinner data after first load/content change
     * @param type
     */
    private void LoadSpinnerData(CategoryType type){
           Spinner spinner = expenseCatSpinner;
           if(type==CategoryType.REVENUE)
               spinner = revenueCatSpinner;
            ArrayList<String> list = CategoryManager.GetCategoryNames(type);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
}
