package za.co.sacpo.grant.xCubeStudent.queries;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import za.co.sacpo.grant.R;

public class Fullscreen extends AppCompatActivity {

    private static final String TAG = "fullscreen";
    private Context context;
    ImageView image_full;
    int SelectedId;
     String get_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

       /* if(getIntent().hasExtra("byteArray")) {
            ImageView _imv= new ImageView(this);
            Bitmap _bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            _imv.setImageBitmap(_bitmap);
        }
        */



       /* Bundle activeInputUri = getIntent().getExtras();
        get_image = activeInputUri.getString("get_image");
*/
      //  image_full.setImageResource(R.drawable.get_image);
        image_full = (ImageView) findViewById(R.id.image_full);

        Intent callingActivityIntent = getIntent();
        SelectedId = callingActivityIntent.getIntExtra("id",114);

        if(callingActivityIntent !=null){
            Uri inputUri = callingActivityIntent.getData();
            if(inputUri != null && image_full != null){
                Picasso.get().load(inputUri).resize(130,130).placeholder(R.mipmap.user).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.mipmap.user).into(image_full);
            }

             if (getIntent().hasExtra("byteArray")){
               Bitmap bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent()
                       .getByteArrayExtra("byteArray").length);
               image_full.setImageBitmap(bitmap);
           }

            }

        }
  }


