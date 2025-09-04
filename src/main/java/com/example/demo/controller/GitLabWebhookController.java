package com.example.demo.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/webhook")
public class GitLabWebhookController {

    private static final Logger logger = Logger.getLogger(GitLabWebhookController.class.getName());

    // 建议从配置文件或环境变量中获取
    private static final String GITLAB_WEBHOOK_SECRET = "123456";

    /**
     * 接收GitLab WebHook事件
     *
     * @param payload 事件负载
     * @param xGitlabToken GitLab发送的验证令牌
     * @param xGitlabEvent 事件类型
     * @param xGitlabSignature 事件签名
     * @return 响应状态
     */
    @PostMapping("/gitlab")
    public ResponseEntity<String> handleGitLabWebhook(
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "X-Gitlab-Token", required = false) String xGitlabToken,
            @RequestHeader(value = "X-Gitlab-Event", required = false) String xGitlabEvent,
            @RequestHeader(value = "X-Gitlab-Signature", required = false) String xGitlabSignature) {

        // 日志记录接收到的事件
        logger.info("Received GitLab event: " + xGitlabEvent);

        // 验证WebHook（根据配置的secret进行验证）
//        if (!validateWebhook(xGitlabToken, xGitlabSignature, payload.toString())) {
//            logger.warning("WebHook validation failed");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Validation failed");
//        }

        // 处理不同类型的事件
        if (xGitlabEvent != null) {
            switch (xGitlabEvent) {
                case "Push Hook":
                    handlePushEvent(payload);
                    break;
                case "Merge Request Hook":
                    handleMergeRequestEvent(payload);
                    break;
                case "Issue Hook":
                    handleIssueEvent(payload);
                    break;
                default:
                    logger.info("Unhandled event type: " + xGitlabEvent);
            }
        }

        return ResponseEntity.ok("WebHook received successfully");
    }

    /**
     * 验证WebHook的合法性
     */
    private boolean validateWebhook(String token, String signature, String payload) {
        // 验证令牌（如果配置了的话）
        if (GITLAB_WEBHOOK_SECRET != null && !GITLAB_WEBHOOK_SECRET.isEmpty()) {
            // 对于较新的GitLab版本，使用signature进行验证
            if (signature != null) {
                return verifySignature(payload, signature);
            }
            // 对于旧版本，使用token进行验证
            else if (token != null) {
                return GITLAB_WEBHOOK_SECRET.equals(token);
            }
            // 没有提供验证信息
            return false;
        }
        // 未配置secret，不进行验证（不推荐在生产环境使用）
        return true;
    }

    /**
     * 验证GitLab签名
     */
    private boolean verifySignature(String payload, String signature) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    GITLAB_WEBHOOK_SECRET.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );
            mac.init(secretKeySpec);

            byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String calculatedSignature = "sha256=" + Base64.getEncoder().encodeToString(digest);

            return calculatedSignature.equals(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.severe("Error verifying signature: " + e.getMessage());
            return false;
        }
    }

    /**
     * 处理推送事件
     */
    private void handlePushEvent(Map<String, Object> payload) {
        logger.info("Handling push event: " + payload.get("ref"));
        logger.info("payload:"+ JSONObject.toJSONString( payload));
        // 在这里实现推送事件的处理逻辑
        // 例如：触发CI/CD流程、发送通知等
    }

    /**
     * 处理合并请求事件
     */
    private void handleMergeRequestEvent(Map<String, Object> payload) {
        logger.info("Handling merge request event");
        logger.info("payload:"+ JSONObject.toJSONString( payload));
        // 在这里实现合并请求事件的处理逻辑
        // 例如：自动审核、触发测试等
    }

    /**
     * 处理Issue事件
     */
    private void handleIssueEvent(Map<String, Object> payload) {
        logger.info("Handling issue event");
        logger.info("payload:"+ JSONObject.toJSONString( payload));
        // 在这里实现Issue事件的处理逻辑
    }
}
