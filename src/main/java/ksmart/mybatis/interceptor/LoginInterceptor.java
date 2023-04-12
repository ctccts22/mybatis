package ksmart.mybatis.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Component
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberMapper memberMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("SID");

        Cookie cookie = WebUtils.getCookie(request, "loginKeepId");

        if (cookie != null) {
            String loginId = cookie.getValue();
            if (sessionId == null) {
                Member memberInfo = memberMapper.getMemberInfoById(loginId);
                if (memberInfo != null) {
                    String memberLevel = memberInfo.getMemberLevel();
                    String memberName = memberInfo.getMemberName();
                    session.setAttribute("SID", loginId);
                    session.setAttribute("SLEVEL", memberLevel);
                    session.setAttribute("SNAME", memberName);
                }
            }
        }

        if (sessionId == null) {
            response.sendRedirect("/member/login");
            return false;
        } else {
            String memberLevel = (String) session.getAttribute("SLEVEL");
            String requestURI = request.getRequestURI();
            if ("2".equals(memberLevel)) {
                if (requestURI.indexOf("/member/") > -1) {
                    response.sendRedirect("/");
                    return false;
                }

                } else if ("3".equals(memberLevel)) {
                    if (requestURI.indexOf("/member/") > -1
                            || requestURI.indexOf("/goods/add") > -1
                            || requestURI.indexOf("/goods/modify") > -1
                            || requestURI.indexOf("/goods/remove") > -1) {
                        response.sendRedirect("/");
                        return false;
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
