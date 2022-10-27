package response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@ApiModel("BaseResponseBody")
public class BaseResponseBody {
    @ApiModelProperty(name="응답 메시지", example = "SUCCESS")
    String message = null;
    @ApiModelProperty(name="응답 데이터", example = "JSON")
    Object data = null;

    public BaseResponseBody() {}

    public BaseResponseBody(String message){
        this.message = message;
    }

    public BaseResponseBody(String message, Object data){
        this.data = data;
        this.message = message;
    }

    public static BaseResponseBody of(String message, Object data) {
        BaseResponseBody body = new BaseResponseBody();
        body.message = message;
        body.data = data;
        return body;
    }
    public static BaseResponseBody of(String message) {
        BaseResponseBody body = new BaseResponseBody();
        body.message = message;
        return body;
    }
}
