package com.ssafy.gganbu.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ssafy.gganbu.db.entity.Patients;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Service("qrService")
public class QrServiceImpl implements QrService {
    @Override
    public BitMatrix getQrCode(String url) throws WriterException {
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new WriterException("fail to create QR code");
        }
        return bitMatrix;
    }

    @Override
    public String createQrImage(BitMatrix qr, Patients patient) throws IOException {
        System.out.println("QrServiceImpl.createQrImage");
//        저장할 폴더 경로 설정
        Path path = Path.of("/tmp/gganbu/patient/" + patient.getPatientId());
        if(!path.toFile().exists()){
            System.out.println("create directory");
            path.toFile().mkdirs();
        }
        // bitMatrix를 png로 변환
        try {
             path = Path.of("/tmp/gganbu/patient/" + patient.getPatientId()+"/qr.png");
            System.out.println("new Path : " + path);
            MatrixToImageWriter.writeToPath(qr, "png", path);
        } catch (IOException e) {
            throw new IOException("fail to create QR image");
        }
        System.out.println("path : " + path.toString());
        System.out.println("path : " + path);
        return path.toString();

    }

    @Override
    public Resource loadFileAsResource(String patientId) {
        try{
            Path filePath = Path.of("/tmp/gganbu/patient/" + patientId + "/qr.png");
            System.out.println("filePath : " + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                System.out.println("path : " + filePath);
                throw new RuntimeException("File not found " + patientId);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

