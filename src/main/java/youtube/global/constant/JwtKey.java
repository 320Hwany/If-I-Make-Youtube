package youtube.global.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtKey {

    private JwtKey() {
    }

    public static String JWT_KEY;

    @Value("${jwt.key}")
    public void setJwtKey(String jwtKey) {
        JWT_KEY = jwtKey;
    }
}
