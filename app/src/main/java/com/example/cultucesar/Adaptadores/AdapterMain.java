package com.example.cultucesar.Adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cultucesar.R;

public class AdapterMain extends PagerAdapter {

    private Context context;
    private Integer sliderImagenes[] = new Integer[] {R.drawable.pagina_home1,R.drawable.pagina_home2,R.drawable.pagina_home3};

    public AdapterMain(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setImageResource(sliderImagenes[position]);

        container.addView(imageView,0);
        return imageView;
    }


    @Override
    public int getCount() {
        return sliderImagenes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

}
