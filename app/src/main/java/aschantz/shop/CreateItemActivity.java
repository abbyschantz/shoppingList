package aschantz.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import aschantz.shop.data.Item;

/**
 * Created by aschantz on 11/10/16.
 */
public class CreateItemActivity extends AppCompatActivity {

    public static final String KEY_ITEM = "KEY_ITEM";
    private Spinner spinnerItemType;
    private EditText etItem;
    private EditText etItemPrice;
    private EditText etItemDesc;
    private CheckBox etCbPurchased;
    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemToEdit = (Item) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }

        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemtypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etItem = (EditText) findViewById(R.id.etItemName);
        etItemPrice = (EditText) findViewById(R.id.etItemPrice);
        etItemDesc = (EditText) findViewById(R.id.etItemDesc);
        etCbPurchased = (CheckBox) findViewById(R.id.etCbPurchased);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmpty(etItem) == true && isEmpty(etItemPrice)==true ) {
                    emptyMessage(getResources().getText(R.string.emptyBoth).toString());
                } else if(isEmpty(etItem) == true) {
                    emptyMessage(getResources().getText(R.string.emptyItem).toString());
                } else if(isEmpty(etItemPrice) == true) {
                    emptyMessage(getResources().getText(R.string.emptyPrice).toString());
                } else {
                    saveItem();
                }
            }
        });

        if (itemToEdit != null) {
            etItem.setText(itemToEdit.getItemName());
            etItemPrice.setText(itemToEdit.getPrice());
            etItemDesc.setText(itemToEdit.getDescription());
            spinnerItemType.setSelection(itemToEdit.getItemType().getValue());
            etCbPurchased.setChecked(itemToEdit.isBought());
        }
    }

    private void saveItem() {
        Intent intentResult = new Intent();
        Item itemResult = null;
        if (itemToEdit != null) {
            itemResult = itemToEdit;
        } else {
            itemResult = new Item();
            itemResult.setPrice("test price");
        }

        itemResult.setItemName(etItem.getText().toString());
        itemResult.setPrice(etItemPrice.getText().toString());
        itemResult.setDescription(etItemDesc.getText().toString());
        itemResult.setItemType(
                Item.ItemType.fromInt(spinnerItemType.getSelectedItemPosition()));

        itemResult.setBought(etCbPurchased.isChecked());

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    private void emptyMessage(String message) {
        Snackbar.make(etItem, message, Snackbar.LENGTH_LONG).show();
    }
}
