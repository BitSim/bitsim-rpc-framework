package github.bitsim;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
@AllArgsConstructor
public class User implements Serializable {
    public String username;
    public String password;
}
