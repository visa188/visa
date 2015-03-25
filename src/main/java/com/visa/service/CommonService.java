package com.visa.service;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.XmlFactoryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.visa.common.constant.Constant;

@Service
public class CommonService {
    private static Log logger = LogFactory.getLog(CommonService.class);

    @Resource
    private VelocityEngine velocityEngine;

    private ToolContext toolContext;

    private ToolContext getToolContext() {
        if (toolContext == null) {
            try {
                XmlFactoryConfiguration configuration = new XmlFactoryConfiguration(true);
                configuration.read(new ClassPathResource(Constant.TOOL_BOX).getInputStream());

                ToolManager manager = new ToolManager(false, false);
                manager.configure(configuration);
                toolContext = manager.createContext();
            } catch (Exception e) {
                logger.error("Init toolbox error!", e);
            }
        }
        return toolContext;
    }

    public String renderVelocity(String tplName, Map<String, Object> parameterMap) throws ResourceNotFoundException,
            ParseErrorException, Exception {
        VelocityContext velocityContext = new VelocityContext(getToolContext());
        if (parameterMap != null) {
            for (Entry<String, Object> entry : parameterMap.entrySet()) {
                velocityContext.put(entry.getKey(), entry.getValue());
            }
        }

        Template template = velocityEngine.getTemplate(tplName);
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);

        return writer.toString();
    }

}
