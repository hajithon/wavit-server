package xyz.wavit.global.security;

import static xyz.wavit.global.constant.SecurityConstant.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.wavit.domain.auth.application.JwtService;
import xyz.wavit.domain.auth.dto.AccessTokenDto;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessTokenValue = extractAccessTokenFromHeader(request);

        if (accessTokenValue == null) {
            filterChain.doFilter(request, response);
            return;
        }

        AccessTokenDto accessTokenDto = jwtService.parseAccessToken(accessTokenValue);
        setAuthenticationToContext(accessTokenDto);

        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(ACCESS_TOKEN_HEADER_PREFIX))
                .map(header -> header.replace(ACCESS_TOKEN_HEADER_PREFIX, ""))
                .orElse(null);
    }

    private void setAuthenticationToContext(AccessTokenDto token) {
        log.info("[JwtFilter] 로그인 성공: userId={}", token.userId());
        UserDetails userDetails = new CustomUserDetails(token.userId(), token.userRole());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
