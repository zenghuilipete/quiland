package creative.air.spring.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeforeLogAdvice {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before("execution(public * playing*(..))")
    public void log(JoinPoint joint) {
        logger.info("----BeforeLog----");
        logger.info(joint.getSignature().getDeclaringTypeName());
        logger.info(joint.getSignature().getName());
    }
}
