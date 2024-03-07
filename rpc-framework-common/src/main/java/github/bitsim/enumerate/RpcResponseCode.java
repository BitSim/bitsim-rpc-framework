package github.bitsim.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@AllArgsConstructor
@Getter
public enum RpcResponseCode implements Serializable {
    SUCCESS(200,"success"),
    NOT_FOUND_CLASS(500,"没有找到指定类"),
    FAIL(500,"fail");
    private final int code;
    private final String message;


}
