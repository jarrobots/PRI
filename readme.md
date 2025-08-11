In order to get this work, as long are we aren't supposed to have separate schema, execute this query in db browsing tool(ie pgAdmin, IntellijUltimate)

GRANT USAGE, CREATE ON SCHEMA test25 TO api;
GRANT SELECT ON ALL TABLES IN SCHEMA test25 TO api;

And also, i decided to use "thm-" prefix for each of our tables names.
