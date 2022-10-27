package com.ssafy.gganbu.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

@Service("qrService")
public class QrServiceImpl implements QrService {
    @Override
    public BitMatrix getQrCode(String url) {
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }
}

