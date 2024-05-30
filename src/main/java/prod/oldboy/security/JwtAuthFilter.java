package prod.oldboy.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import prod.oldboy.service.AccountService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtTokenGenerator jwtTokenGenerator;
    private final AccountService accountService;

    @Override
    @SneakyThrows
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) {
        String authRequestHeader = request.getHeader(HEADER_NAME);
        String tokenFromRequest = null;
        if (authRequestHeader != null && authRequestHeader.startsWith(BEARER_PREFIX)) {
            tokenFromRequest = authRequestHeader.substring(BEARER_PREFIX.length());
        }
            try {
                String accountLogin = jwtTokenGenerator.extractUserName(tokenFromRequest);
                UserDetails userDetails = new JwtLoginEntity(accountService.findAccountByLogin(accountLogin));
                if (jwtTokenGenerator.isValid(tokenFromRequest, userDetails)) {
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            } catch (Exception ignored){
        }
        filterChain.doFilter(request, response);
    }
}