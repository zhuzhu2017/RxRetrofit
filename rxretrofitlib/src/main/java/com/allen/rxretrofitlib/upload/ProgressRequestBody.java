package com.allen.rxretrofitlib.upload;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 自定义回调加载速度类RequestBody
 * Created by allen on 2017/12/6.
 */

public class ProgressRequestBody extends RequestBody {

    private RequestBody body;
    private UploadProgressListener listener;

    public ProgressRequestBody(RequestBody body, UploadProgressListener listener) {
        this.body = body;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        CountSink countSink = new CountSink(sink);
        //将CountingSink转化为BufferedSink供writeTo()使用
        BufferedSink bufferedSink = Okio.buffer(countSink);
        body.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    /**
     * 数据缓冲内部类
     */
    protected final class CountSink extends ForwardingSink {
        private long byteWritten;

        CountSink(Sink delegate) {
            super(delegate);
        }

        /**
         * 上传时调用该方法,在其中调用回调函数将上传进度暴露出去,该方法提供了缓冲区的自己大小
         *
         * @param source    上传文件源
         * @param byteCount 上传的字节数
         * @throws IOException 抛出的IO异常
         */
        @Override
        public void write(@NonNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            listener.onProgress(byteWritten, contentLength());
        }
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return body.contentLength();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
