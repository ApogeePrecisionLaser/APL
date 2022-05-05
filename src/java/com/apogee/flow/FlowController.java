package com.apogee.flow;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vikrant
 */
public class FlowController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        int idd=0;
        request.setAttribute("mode", "screens");
        int id = Integer.parseInt(request.getParameter("nxt"));
        //idd++;
        
        
        request.setAttribute("idd", id+1);
        request.setAttribute("iddd", id-1);
        
        // organization_type organization_name org_office_type orgOffice designation orgOfficeDesignationMap keyPerson item_type
        // item_name manufacturer model_name inventory_basic inventory item_authorization indent approve_indent check_inventory
        
        if (id == 1) {
            request.getRequestDispatcher("view/Flow/city.jsp").forward(request, response);
        }else if(id == 2){
            request.getRequestDispatcher("view/Flow/organization_type.jsp").forward(request, response);
        }else if(id == 3){
            request.getRequestDispatcher("view/Flow/organization_name.jsp").forward(request, response);
        }else if(id == 4){
            request.getRequestDispatcher("view/Flow/org_office_type.jsp").forward(request, response);
        }else if(id == 5){
            request.getRequestDispatcher("view/Flow/orgOffice.jsp").forward(request, response);
        }else if(id == 6){
            request.getRequestDispatcher("view/Flow/designation.jsp").forward(request, response);
        }else if(id == 7){
            request.getRequestDispatcher("view/Flow/orgOfficeDesignationMap.jsp").forward(request, response);
        }else if(id == 8){
            request.getRequestDispatcher("view/Flow/keyperson.jsp").forward(request, response);
        }else if(id == 9){
            request.getRequestDispatcher("view/Flow/item_type.jsp").forward(request, response);
        }else if(id == 10){
            request.getRequestDispatcher("view/Flow/item_name.jsp").forward(request, response);
        }else if(id == 11){
            request.getRequestDispatcher("view/Flow/manufacturer.jsp").forward(request, response);
        }else if(id == 12){
            request.getRequestDispatcher("view/Flow/model_name.jsp").forward(request, response);
        }else if(id == 13){
            request.getRequestDispatcher("view/Flow/inventory_basic.jsp").forward(request, response);
        }else if(id == 14){
            request.getRequestDispatcher("view/Flow/inventory.jsp").forward(request, response);
        }else if(id == 15){
            request.getRequestDispatcher("view/Flow/item_authorization.jsp").forward(request, response);
        }else if(id == 16){
            request.getRequestDispatcher("view/Flow/indent.jsp").forward(request, response);
        }else if(id == 17){
            request.getRequestDispatcher("view/Flow/approve_indent.jsp").forward(request, response);
        }else if(id == 18){
            request.getRequestDispatcher("view/Flow/check_inventory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
