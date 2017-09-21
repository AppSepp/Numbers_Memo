package bertaworld.bertaworldnumbers;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import bertaworld.mylibrary.AutoFitText;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final Integer[] Kolor;
    private final float[] Durchsichtig;
    public Typeface type;


    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid, Integer[] Kolor, float[] Durchsichtig) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.Kolor=Kolor;
        this.Durchsichtig=Durchsichtig;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        //TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        AutoFitText ItemBox = (AutoFitText) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        //txtTitle.setText(itemname[position]);
        ItemBox.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        type = Typeface.createFromAsset(context.getAssets(), "fonts/segoesc.ttf");
        ItemBox.setTextColor(Kolor[position]);
        rowView.setAlpha(Durchsichtig[position]);
        ItemBox.setTypeface(type);
        //extratxt.setText("Description "+itemname[position]);
        return rowView;
    }

    ;

}
