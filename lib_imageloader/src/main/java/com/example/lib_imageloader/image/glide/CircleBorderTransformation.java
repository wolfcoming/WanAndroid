package com.example.lib_imageloader.image.glide;

import android.graphics.Bitmap;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class CircleBorderTransformation extends BitmapTransformation {
    // The version of this transformation, incremented to correct an error in a previous version.
    // See #455.
    private static final int VERSION = 1;
    private static final String ID = "com.yilahuo.driftbottle.loader.transform.CircleBorderTransformation." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final float borderWidth;
    private final int borderColor;

    /**
     * Provide the radii to round the corners of the bitmap.
     */
    public CircleBorderTransformation(float borderWidth, @ColorInt int borderColor) {
        Preconditions.checkArgument(borderWidth > 0, "borderWidth must be more the 0.");

        this.borderWidth = borderWidth;
        this.borderColor = borderColor;

    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(
            @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        // 自定义的 TransformationUtils
        return GlideTransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight, borderWidth, borderColor);
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof CircleBorderTransformation) {
            CircleBorderTransformation other = (CircleBorderTransformation) o;
            return borderWidth == other.borderWidth
                    && borderColor == other.borderColor;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = Util.hashCode(ID.hashCode(), Util.hashCode(borderWidth));
        return Util.hashCode(borderColor, hashCode);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData =
                ByteBuffer.allocate(8)
                        .putFloat(borderWidth)
                        .putInt(borderColor)
                        .array();
        messageDigest.update(radiusData);
    }
}