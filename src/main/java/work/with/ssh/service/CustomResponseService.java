package work.with.ssh.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import work.with.ssh.api.model.CustomResponse;

@Service
public class CustomResponseService {
// this class is supposed to return the custom response with all expected data related to the HMND node
    
    private List<CustomResponse> listOfData; 

    public CustomResponseService() {
        listOfData = new ArrayList<>();

        CustomResponse resp1 = new CustomResponse("45.131.182.137", "___Natalka_Lu___", "Active", "till next Wed", 10);
        CustomResponse resp2 = new CustomResponse("185.244.218.254", "___Sasha_Lu___", "Active", "till next Tue", 145);
    
        listOfData.addAll(Arrays.asList(resp1, resp2));
        System.out.println(" *** ALOU DEBUG *** print listOfData array content: " + listOfData.get(0).getHost());
    }

    public CustomResponse getCustomService(String host) {
        for (CustomResponse data : listOfData) {
            System.out.println(" - CustomResponse object content is " + data.getHost());
            if (host.equalsIgnoreCase(data.getHost())) {
                System.out.println(" --== got the host ==-- ");
                return data;
            } else {
                System.out.println(" - no data to show - ");
                return new CustomResponse("alou-localhost", "some", "next", "nothing", 5);
            }

        }
        
        return null;
    }
    
}
