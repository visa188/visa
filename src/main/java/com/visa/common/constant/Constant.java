package com.visa.common.constant;

/**
 * @author user
 */
public final class Constant {
    /**
     * constructor
     */
    private Constant() {
    }

    /**
     * session 中 userId 的 key
     */
    public static final String SESSION_USER = "session_user";

    /**
     * Error message key
     */
    public static final String ERROR_MSG = "error_msg";
    /**
     * velocity toolbox
     */
    public static final String TOOL_BOX = "conf/toolbox.xml";

    /**
     * 默认编码设置
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * PAGE_COUNT
     */
    public static final int PAGE_COUNT = 20;

    /**
     * PAGE_COUNT
     */
    public static final int LINE_PAGE_COUNT = 20;

    /**
     * PAGE_OFFSET
     */
    public static final int PAGE_OFFSET = 10;
    /**
     * PAGE_OFFSET
     */
    public static final int LINE_PAGE_OFFSET = 10;

    public static final String ORDER_PREFIX_BJ = "BJ";

    public static final int SUPER_ADMIN_ROLE_ID = 0;

    public static final int OPERATOR_TYPE_ADD = 1;

    public static final int OPERATOR_TYPE_UPDATE = 2;

    public static final int OPERATOR_TYPE_DELETE = 3;
}
