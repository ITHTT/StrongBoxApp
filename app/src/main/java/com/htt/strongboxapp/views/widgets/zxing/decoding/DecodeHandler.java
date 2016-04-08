/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htt.strongboxapp.views.widgets.zxing.decoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.htt.strongboxapp.R;
import com.htt.strongboxapp.activitys.QRCodeScanActivity;
import com.htt.strongboxapp.views.widgets.zxing.camera.CameraManager;
import com.htt.strongboxapp.views.widgets.zxing.camera.PlanarYUVLuminanceSource;

/**
 * 扫描过程处理类
 */

final class DecodeHandler extends Handler {

  private static final String TAG = DecodeHandler.class.getSimpleName();

  private final QRCodeScanActivity activity;
  /**二维码数据格式封装的类*/
  private final MultiFormatReader multiFormatReader;

  DecodeHandler(QRCodeScanActivity activity, Hashtable<DecodeHintType, Object> hints) {
    multiFormatReader = new MultiFormatReader();
    multiFormatReader.setHints(hints);
    this.activity = activity;
  }

  @Override
  public void handleMessage(Message message) {
    switch (message.what) {
      case R.id.decode:
        //Log.d(TAG, "Got decode message");
        decode((byte[]) message.obj, message.arg1, message.arg2);
        break;
      case R.id.quit:
        Looper.myLooper().quit();
        break;
    }
  }

  /**
   * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
   * reuse the same reader objects from one decode to the next.
   *
   * @param data   The YUV preview frame.
   * @param width  The width of the preview frame.
   * @param height The height of the preview frame.
   */
  private void decode(byte[] data, int width, int height) {
    long start = System.currentTimeMillis();
    Result rawResult = null;
    
    //modify here
    byte[] rotatedData = new byte[data.length];
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++)
            rotatedData[x * height + height - y - 1] = data[x + y * width];
    }
    int tmp = width; // Here we are swapping, that's the difference to #11
    width = height;
    height = tmp;
    
    PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, width, height);
    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
    try {
      rawResult = multiFormatReader.decodeWithState(bitmap);
    } catch (ReaderException re) {
      // continue
    } finally {
      multiFormatReader.reset();
    }
    if (rawResult != null) {
      long end = System.currentTimeMillis();
      Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
      Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded, rawResult);
      Bitmap picture=source.renderCroppedGreyscaleBitmap();
      if(picture!=null){
        Bitmap compressBitmap=compressBitmap(picture,200*1024);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
        message.setData(bundle);
      }
      //Log.d(TAG, "Sending decode succeeded message...");
      message.sendToTarget();
    } else {
      Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
      message.sendToTarget();
    }
  }

  /**
   * caculate the bitmap sampleSize
   * @return
   */
  public final  int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (rqsW == 0 || rqsH == 0) return 1;
    if (height > rqsH || width > rqsW) {
      final int heightRatio = Math.round((float) height/ (float) rqsH);
      final int widthRatio = Math.round((float) width / (float) rqsW);
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }
    return inSampleSize;
  }


  /**
   * 压缩指定byte[]图片，并得到压缩后的图像
   * @param bts
   * @param reqsW
   * @param reqsH
   * @return
   */
  public final  Bitmap compressBitmap(byte[] bts, int reqsW, int reqsH) {
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
    options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
  }

  private Bitmap compressBitmap(Bitmap bitmap, long maxBytes) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
      int options = 90;
      while (baos.toByteArray().length > maxBytes) {
        baos.reset();
        bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
        options -= 10;
      }
      byte[] bts = baos.toByteArray();
      Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
      baos.close();
      return bmp;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }
}
