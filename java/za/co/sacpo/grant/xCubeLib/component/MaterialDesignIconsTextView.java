package za.co.sacpo.grant.xCubeLib.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MaterialDesignIconsTextView extends androidx.appcompat.widget.AppCompatTextView{
	private static Typeface sMaterialDesignIcons;  
	public MaterialDesignIconsTextView(Context paramContext){
		this(paramContext, null);
	}
    public MaterialDesignIconsTextView(Context paramContext, AttributeSet paramAttributeSet){
    	this(paramContext, paramAttributeSet, 0);
    }
    public MaterialDesignIconsTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt){
    	super(paramContext, paramAttributeSet, paramInt);
    	if (isInEditMode()) {
			return;
    	}
    	setTypeface();
    }  
    private void setTypeface(){
    	if (sMaterialDesignIcons == null) {
    		sMaterialDesignIcons = Typeface.createFromAsset(getContext().getAssets(), "fonts/MaterialDesignIcons.ttf");
    	}
    	setTypeface(sMaterialDesignIcons);
    }
}
