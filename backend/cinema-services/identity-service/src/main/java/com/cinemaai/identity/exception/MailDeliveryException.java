package com.cinemaai.identity.exception;

public class MailDeliveryException extends RuntimeException {
    public MailDeliveryException() {
        super("Không thể gửi email xác thực. Vui lòng thử lại sau.");
    }
}
