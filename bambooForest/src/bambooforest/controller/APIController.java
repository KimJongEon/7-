package bambooforest.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bambooforest.action.Action;


/**
 * Servlet implementation class APIController
 */
@WebServlet(
      urlPatterns = { 
            "/APIController", 
            "/api/*"
      }, 
      initParams = { 
            @WebInitParam(name = "property", value = "api.properties")
      })
public class APIController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   //명령어와 클래스를 쌍으로 저장
   private Map<String,Object> commandMap = new HashMap<String,Object>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public APIController() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see Servlet#init(ServletConfig)
    */
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
              // initParams에서 propertyConfig의 값을 읽어옴
              String props = config.getInitParameter("property");
              String realFolder = "/property"; // properties 파일이 저장된 폴더

              // 웹 애플리케이션 루트 경로
              ServletContext context = config.getServletContext();
              // realFolder를 웹 애플리케이션 시스템상의 절대 경로로 변경
              String realPath = context.getRealPath(realFolder) + "\\" + props;

              // 명령어와 처리 클래스의 매핑정보를 저장할 Properties 객체 생성
              Properties pr = new Properties();
              FileInputStream f = null;

              try {
                 // command.properties의 파일의 내용을 읽어옴
                 f = new FileInputStream(realPath);
                 // command.properties의 내용을 Properties 객체 pr에 저장
                 pr.load(f);

              } catch (IOException e) {
                 System.out.println("Properties 객체 생성 실패");
              } finally {
                 if (f != null)
                    try {
                       f.close();
                    } catch (IOException ex) {
                       System.out.println("에러");
                    }
              }

              // Set 객체의 iterator() 메소드를 사용해 Iterator객체를 얻어냄
              Iterator<?> keyIter = pr.keySet().iterator();
              // Iterator 객체에 저장된 명령어와 처리 클래스를 commandMap에 저장
              while (keyIter.hasNext()) {
                 String command = (String) keyIter.next(); // 왼쪽 Key값
                 String className = pr.getProperty(command); // 오른쪽 Value값
                 
                 try {
                    Class<?> commandClass = Class.forName(className); //
                    Object commandInstance = commandClass.newInstance(); //
                    commandMap.put(command, commandInstance);
                 } catch (ClassNotFoundException e) {
                    System.out.println("에러1");
                 } catch (InstantiationException e) {
                    System.out.println("에러2");
                 } catch (IllegalAccessException e) {
                    System.out.println("에러3");
                 }
              }
           
     }


   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      process(request,response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      process(request,response);
   }

   private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
      String result = null;
      Action action = null;
         try {
            String command = request.getRequestURI(); // 주소창에 적은걸 읽을 수 있음
            
            if(command.indexOf(request.getContextPath())==0) {
               command=command.substring(request.getContextPath().length()); 
               }
               action = (Action)commandMap.get(command); // MAP에서 검색
               if(action == null)
                  action = (Action)commandMap.get("/api/hello");
               result = action.process(request, response); // 요청을 처리해달라 요청, jsp파일을 리턴해줌
               System.out.println("api" + result);
         } catch (Throwable e) {
            // TODO: handle exception
            e.printStackTrace();
         }
         //그냥 주면 웹브라우저가 JSON인지 모름 , html이 아니라 text라고 말해줌
         response.setContentType("text; charset=UTF-8");
         //action 이 준 JSON 을 바로 AJAX에게 리턴
         response.getWriter().println(result);

      }
}