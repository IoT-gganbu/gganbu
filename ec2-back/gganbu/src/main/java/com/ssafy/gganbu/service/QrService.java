package com.ssafy.gganbu.service;

import com.google.zxing.common.BitMatrix;

public interface QrService {
    BitMatrix getQrCode(String url);
}
