package mx.edu.uacm.is.slt.as.sistpolizas.controller;

import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientLocal;
import mx.edu.uacm.is.slt.as.sistpolizas.apiClient.ApiClientRemoto;


@Controller
public class IndexController {
    
    public String index(Model model) throws Exception{
        model.addAttribute("curp", new String());
        return "index";
    }
}
