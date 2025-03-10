package liquibase;

import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationDefinition;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Configuration container for global properties.
 */
public class GlobalConfiguration implements AutoloadedConfigurations {

    public static final ConfigurationDefinition<String> DATABASECHANGELOG_TABLE_NAME;
    public static final ConfigurationDefinition<String> DATABASECHANGELOGLOCK_TABLE_NAME;
    public static final ConfigurationDefinition<String> LIQUIBASE_TABLESPACE_NAME;
    public static final ConfigurationDefinition<String> LIQUIBASE_CATALOG_NAME;
    public static final ConfigurationDefinition<String> LIQUIBASE_SCHEMA_NAME;
    public static final ConfigurationDefinition<String> OUTPUT_LINE_SEPARATOR;
    public static final ConfigurationDefinition<String> OUTPUT_FILE_ENCODING;
    public static final ConfigurationDefinition<Charset> FILE_ENCODING;
    public static final ConfigurationDefinition<Long> CHANGELOGLOCK_WAIT_TIME;
    public static final ConfigurationDefinition<Long> CHANGELOGLOCK_POLL_RATE;
    public static final ConfigurationDefinition<Boolean> CONVERT_DATA_TYPES;
    public static final ConfigurationDefinition<Boolean> GENERATE_CHANGESET_CREATED_VALUES;
    public static final ConfigurationDefinition<Boolean> AUTO_REORG;
    public static final ConfigurationDefinition<Boolean> DIFF_COLUMN_ORDER;
    public static final ConfigurationDefinition<Boolean> ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA;
    public static final ConfigurationDefinition<Boolean> GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION;
    public static final ConfigurationDefinition<Boolean> INCLUDE_CATALOG_IN_SPECIFICATION;
    public static final ConfigurationDefinition<Boolean> SHOULD_SNAPSHOT_DATA;
    public static final ConfigurationDefinition<Boolean> FILTER_LOG_MESSAGES;
    public static final ConfigurationDefinition<Boolean> HEADLESS;
    public static final ConfigurationDefinition<Boolean> STRICT;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase");

        DATABASECHANGELOG_TABLE_NAME = builder.define("databaseChangelogTableName", String.class)
                .addAliasKey("liquibase.databaseChangeLogTableName")
                .setDescription("Name of table to use for tracking change history")
                .setDefaultValue("DATABASECHANGELOG")
                .build();

        DATABASECHANGELOGLOCK_TABLE_NAME = builder.define("databaseChangelogLockTableName", String.class)
                .addAliasKey("liquibase.databaseChangeLogLockTableName")
                .setDescription("Name of table to use for tracking concurrent Liquibase usage")
                .setDefaultValue("DATABASECHANGELOGLOCK")
                .build();

        CHANGELOGLOCK_WAIT_TIME = builder.define("changelogLockWaitTimeInMinutes", Long.class)
                .addAliasKey("liquibase.changeLogLockWaitTimeInMinutes")
                .setDescription("Number of minutes to wait for the changelog lock to be available before giving up")
                .setDefaultValue(5L)
                .build();

        CHANGELOGLOCK_POLL_RATE = builder.define("changelogLockPollRate", Long.class)
                .addAliasKey("liquibase.changeLogLockPollRate")
                .setDescription("Number of seconds wait between checks to the changelog lock when it is locked")
                .setDefaultValue(10L)
                .build();

        LIQUIBASE_TABLESPACE_NAME = builder.define("liquibaseTablespaceName", String.class)
                .addAliasKey("liquibase.liquibaseTableSpaceName")
                .addAliasKey("liquibase.databaseChangeLogTablespaceName")
                .setDescription("Tablespace to use for Liquibase objects")
                .build();

        LIQUIBASE_CATALOG_NAME = builder.define("liquibaseCatalogName", String.class)
                .addAliasKey("liquibase.catalogName")
                .setDescription("Catalog to use for Liquibase objects")
                .build();

        LIQUIBASE_SCHEMA_NAME = builder.define("liquibaseSchemaName", String.class)
                .addAliasKey("liquibase.schemaName")
                .setDescription("Schema to use for Liquibase objects")
                .build();

        OUTPUT_LINE_SEPARATOR = builder.define("outputLineSeparator", String.class)
                .setDescription("Line separator for output")
                .setDefaultValue(System.getProperty("line.separator"),"Line separator(LF or CRLF) for output. Defaults to OS default")
                .build();

        FILE_ENCODING = builder.define("fileEncoding", Charset.class)
                .setDescription("Encoding to use when reading files. Valid values include: UTF-8, UTF-16, UTF-16BE, UTF-16LE, US-ASCII, or OS to use the system configured encoding.")
                .setDefaultValue(StandardCharsets.UTF_8)
                .setValueHandler(value -> {
                    if (value == null) {
                        return StandardCharsets.UTF_8;
                    }
                    if (value instanceof Charset) {
                        return (Charset) value;
                    }
                    final String valueString = String.valueOf(value);
                    if (valueString.equalsIgnoreCase("os")) {
                        return Charset.defaultCharset();
                    } else {
                        return Charset.forName(valueString);
                    }
                })
                .setCommonlyUsed(true)
                .build();

        OUTPUT_FILE_ENCODING = builder.define("outputFileEncoding", String.class)
                .setDescription("Encoding to use when writing files")
                .setDefaultValue("UTF-8")
                .setCommonlyUsed(true)
                .build();

        CONVERT_DATA_TYPES = builder.define("convertDataTypes", Boolean.class)
                .setDescription("Should Liquibase convert to/from STANDARD data types. Applies to both snapshot and " +
                        "update commands.")
                .setDefaultValue(true)
                .build();

        GENERATE_CHANGESET_CREATED_VALUES = builder.define("generateChangesetCreatedValues", Boolean.class)
                .addAliasKey("liquibase.generateChangeSetCreatedValues")
                .setDescription("Should Liquibase include a 'created' attribute in diff/generateChangelog changesets with" +
                        " the current datetime")
                .setDefaultValue(false)
                .build();

        AUTO_REORG = builder.define("autoReorg", Boolean.class)
                .setDescription("Should Liquibase automatically include REORG TABLE commands when needed?")
                .setDefaultValue(true)
                .build();

        DIFF_COLUMN_ORDER = builder.define("diffColumnOrder", Boolean.class)
                .setDescription("Should Liquibase compare column order in diff operation?")
                .setDefaultValue(true)
                .build();

        ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA = builder.define("alwaysOverrideStoredLogicSchema", Boolean.class)
                .setDescription("When generating SQL for createProcedure, should the procedure schema be forced to the default schema if no schemaName attribute is set?")
                .setDefaultValue(false)
                .build();


        GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION = builder.define("generatedChangesetIdsContainsDescription", Boolean.class)
                .addAliasKey("liquibase.generatedChangeSetIdsContainsDescription")
                .setDescription("Should Liquibase include the change description in the id when generating changesets?")
                .setDefaultValue(false)
                .build();

        INCLUDE_CATALOG_IN_SPECIFICATION = builder.define("includeCatalogInSpecification", Boolean.class)
                .setDescription("Should Liquibase include the catalog name when determining equality?")
                .setDefaultValue(false)
                .build();

        SHOULD_SNAPSHOT_DATA = builder.define("shouldSnapshotData", Boolean.class)
                .setDescription("Should Liquibase snapshot data by default?")
                .setDefaultValue(false)
                .build();

        FILTER_LOG_MESSAGES = builder.define("filterLogMessages", Boolean.class)
                .setDescription("Should Liquibase filter log messages for potentially insecure data?")
                .setDefaultValue(true)
                .build();

        HEADLESS = builder.define("headless", Boolean.class)
                .setDescription("Force liquibase to think it has no access to a keyboard")
                .setDefaultValue(false)
                .setCommonlyUsed(true)
                .build();

        STRICT = builder.define("strict", Boolean.class)
                .setDescription("Be stricter on allowed Liquibase configuration and setup?")
                .setDefaultValue(true)
                .build();
    }
}
