package com.ssafy.gganbu.service;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ssafy.gganbu.db.entity.Patients;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface QrService {
    BitMatrix getQrCode(String url) throws WriterException;

    String createQrImage(BitMatrix qr, Patients patient) throws IOException;

    Resource loadFileAsResource(String fileName);
}
