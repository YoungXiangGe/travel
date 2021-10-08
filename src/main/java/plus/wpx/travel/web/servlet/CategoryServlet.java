package plus.wpx.travel.web.servlet;

import plus.wpx.travel.domain.Category;
import plus.wpx.travel.service.CategoryService;
import plus.wpx.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();

    public void categoryQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> category = service.query();
        writeValue(response, category);
    }

}
