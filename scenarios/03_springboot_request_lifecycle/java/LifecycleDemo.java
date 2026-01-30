package com.devdooly.cookbook.scenario03;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 시나리오 03: Request Lifecycle 데모
 * 
 * 요청 흐름:
 * Client -> Filter -> DispatcherServlet -> HandlerMapping -> Adapter 
 * -> Interceptor(pre) -> Controller -> Service -> Controller 
 * -> MessageConverter(JSON) -> Response
 */
@RestController
public class LifecycleController {

    private final SimpleService simpleService;

    public LifecycleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    // 1. DispatcherServlet이 HandlerMapping을 통해 이 메서드를 찾음
    // 2. HandlerAdapter가 이 메서드를 invoke 함
    @GetMapping("/service")
    public ResponseDto processRequest() {
        System.out.println("[Controller] Received Request at /service");
        
        // 3. 비즈니스 로직 수행
        String result = simpleService.doLogic();
        
        // 4. 객체(ResponseDto) 리턴 -> HttpMessageConverter(Jackson)가 JSON으로 변환
        return new ResponseDto("SUCCESS", result);
    }
}

@Service
class SimpleService {
    public String doLogic() {
        System.out.println("[Service] Executing Business Logic...");
        return "Processed Data";
    }
}

// 응답 DTO (Jackson에 의해 {"status": "...", "data": "..."} 로 변환됨)
class ResponseDto {
    private String status;
    private String data;

    public ResponseDto(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() { return status; }
    public String getData() { return data; }
}
