Module requires following privileges on schema it uses(i.e. test25):

GRANT USAGE, CREATE ON SCHEMA test25 TO api;
GRANT SELECT ON ALL TABLES IN SCHEMA test25 TO api;

Tables created for this module, have thm_ prefixes in its name.

This module is going to generate initial data, one thm_thesis per project, one thm_chapter per every student with non null project_id field.
Following configuration is required in application.yml:
thesis-module:
  generate-theses-and-chapters-on-startup: true