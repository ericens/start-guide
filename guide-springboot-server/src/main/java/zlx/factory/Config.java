package zlx.factory;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@NoArgsConstructor
@Data
@Service
public class Config    {
    String str="bbb";

    @Transactional
    public void  p(){
        log.info("ConfigConfigConfigConfigConfigConfigConfigConfigConfigConfigConfigConfig");
    }
}