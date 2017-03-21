package cn.onekit;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class IMAGE {
	public static Bitmap load(Context context,Uri uri){
		try {
			ContentResolver resolver = context.getContentResolver();
			return MediaStore.Images.Media.getBitmap(resolver, uri);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Bitmap parse(Drawable drawable) // drawable 转换成bitmap
	{
		int width = drawable.getIntrinsicWidth();// 取drawable的长宽
		int height = drawable.getIntrinsicHeight();
		Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565;// 取drawable的颜色格式
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
		Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);// 把drawable内容画到画布中
		return bitmap;
	}

	/**
	 * 缩放
	 * @param image
	 * @param size
     * @return
     */
	public static Bitmap scale(Bitmap image, float size) {
		Matrix matrix = new Matrix();
		matrix.postScale(size, size); // 长和宽放大缩小的比例
		return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
	}

	/**
	 * roundImage() 圆形图片
	 *
	 * @param bitmap
	 *            :图片
	 */
	public static Bitmap roundCorners(Bitmap bitmap) {
		Bitmap rBitmap = bitmap.copy(bitmap.getConfig(), true);
		int width = rBitmap.getWidth();
		int height = rBitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		// @SuppressWarnings("unused")
		// final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
		paint.setColor(color);

		// 以下有两种方法画圆,drawRounRect和drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
		// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(rBitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
		rBitmap.recycle();
		return output;
	}

	/**
	 * compressImage() 压缩图片
	 *
	 * @param sentBitmap
	 *            :图片
	 * @param zip
	 *            :压缩比例(1-100,100表示不压缩。)
	 */
	public static Bitmap compress(Bitmap sentBitmap, int zip) {
		Bitmap image = sentBitmap.copy(sentBitmap.getConfig(), true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, zip, baos);
		image.recycle();
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	/**
	 * 剪裁
	 * @param bitmap	图像
	 * @param rect		裁剪区域
     * @return			返回
     */
	public static Bitmap crop(Bitmap bitmap, Rect rect) {
		return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, null,
				false);
	}

	public static Bitmap zoom(Bitmap bgimage, double newWidth, double newHeight, boolean toMini) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		if (toMini) {
			if (width < newWidth && height < newHeight) {
				return bgimage;
			}
		}
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		// float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	public static Bitmap zoom(Bitmap bgimage, double newWidth, double newHeight) {
		return zoom(bgimage, newWidth, newHeight, false);
	}

	/**
	 * 纯色图片
	 * @param mBitmap	图像
	 * @param color	颜色
     * @return			返回值
     */
	public static Bitmap singleColorColor(Bitmap mBitmap, int color) {
		Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(mAlphaBitmap);
		Paint mPaint = new Paint();
		mPaint.setColor(color);
		Bitmap alphaBitmap = mBitmap.extractAlpha();
		mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);
		return mAlphaBitmap;
	}
}
