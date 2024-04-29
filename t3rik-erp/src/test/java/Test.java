import com.alibaba.druid.filter.config.ConfigTools;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName Test
 * @Description:
 * @Author: 施子安
 * @CreateDate: 2024/4/29 10:55
 * @UpdateUser: 更新人
 * @UpdateDate: 2024/4/29 10:55
 * @UpdateRemark:
 * @Version: 1.0
 */


@SpringBootTest
public class Test {
    @org.junit.Test
    public void method2() throws Exception {
        //定义数据库密码，以123456为例
        String password = "root123ewq";
        //调用druid的工具类来加密密码
        ConfigTools.main(new String[]{password});
//        privateKey:MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAhg2NpQcYOoPyeVZIpX1R7Pq2EHdcH/Hv+LBxVTe2pCbiF0NbLAE+a3PtS2wfdXY0D2GMNou57/e0Q2YDa8gc2wIDAQABAkAI3nywnneL547zWDIWQORauXuyOzNtKaSm34WgDb+XvKJE7dwIBkcwP+sbHKCBt6/+DLRc9PCA0OKQCxd1FpMBAiEAxy7mt2oA2IX6q4ttXHGK1YAAhjjhABfmh9fdXnXAXWkCIQCsSpkKYJD50ik8YCEHILhB4cIuCle/LA8VsZOJjjwrowIgEEA4XLCOXxwXRpW6l3dAD4lBAbJ0URCiI2UuT32l8zkCIBNMg9oKuRKSvPW7Wte+jYUrsaGQnQWX/NSUQI9+9DR7AiATZrvQ2h5aAPzN8rk/HHHTLkpjKwENmRIZsA/4zMIjWg==
//                publicKey:MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIYNjaUHGDqD8nlWSKV9Uez6thB3XB/x7/iwcVU3tqQm4hdDWywBPmtz7UtsH3V2NA9hjDaLue/3tENmA2vIHNsCAwEAAQ==
//                password:BswcOs31Dw5ScXSbuILCfWwqAAWrUvWiLF8BNXVzL4/I9WUne76M5Am1fBPY8Nw8tMFV1H/Z00cB9pU5ct6g4g==
    }
}
