CREATE KEYSPACE IF NOT EXISTS ocds WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS  ocds.regulation_data (
    contract_id text,
    json_data text,
    primary key(contract_id)
);

CREATE TABLE IF NOT EXISTS  ocds.regulation_rules (
    country text,
    pmd text,
    parameter text,
    value text,
    primary key(country, pmd, parameter)
);

CREATE TABLE IF NOT EXISTS  ocds.regulation_templates (
    country text,
    pmd text,
    language text,
    template_id text,
    template text,
    primary key(country, pmd, language, template_id)
);

INSERT INTO regulation_rules (country,pmd,parameter,value) VALUES ('MD','OT','goods_dynamic','{ "templateIds": ["cc-subject"] }');
INSERT INTO regulation_rules (country,pmd,parameter,value) VALUES ('MD','OT','goods_static','{ "templateIds": ["cc-general", "cc-tenderer-1"] }');

INSERT INTO regulation_templates (country,pmd,language,template_id,template) VALUES ('MD','OT','en','cc-general','{"id": "cc-general", "title": "General Responsibilities", "description": "Contract conditions governing the general responsibilities related to both: CA and EO","observations": [{"id": "cc-general-1-1","notes": "Maximum days to inform on invoking Force Majeure","unit": {"name": "day","id": "359","scheme": "MD-CUMC"}},{"id": "cc-general-1-2","notes": "Maximum days to inform on termination intention","unit": {"name": "day","id": "359","scheme": "MD-CUMC"}},{"id": "cc-general-1-3","notes": "Maximum days to respond on termination intention","unit": {"name": "day","id": "359","scheme": "MD-CUMC"}}]}');
INSERT INTO regulation_templates (country,pmd,language,template_id,template) VALUES ('MD','OT','en','cc-tenderer-1','{"id": "cc-tenderer-1","title": "Execution Guarantee","description": "Contract conditions governing the general responsibilities related to EO regarding guarantee","observations": [{"id": "cc-tenderer-1-1","notes": "Guarantee amount","unit": {"name": "percent","id": "746","scheme": "MD-CUMC"}},{"id": "cc-tenderer-1-2","notes": "Guarantee amount provided by the general entrepreneur","unit": {"name": "percent","id": "746","scheme": "MD-CUMC"}},{"id": "cc-tenderer-1-3","notes": "Amount of guarantee in case of successive withholding from partial invoices","unit": {"name": "day","id": "359","scheme": "MD-CUMC"}},{"id": "cc-tenderer-1-4","notes": "Maximum amount in case of successive withholding from partial invoices","unit": {"name": "day","id": "359","scheme": "MD-CUMC"}},{"id": "cc-tenderer-1-5","notes": "Maximum days of guarantee transfer","unit": {"name": "day","id": "359","scheme": "MD-CUMC"}}]}');
INSERT INTO regulation_templates (country,pmd,language,template_id,template) VALUES ('MD','OT','en','cc-general','{"id": "cc-subject","title": "item.description: subject specification","description": "Description of procured items of subject of contracting process","observations": [{"id": "cc-subject-1","notes": "Item model"},{"id": "cc-subject-2","notes": "Country of origin"},{"id": "cc-subject-3","notes": "Producer"},{"id": "cc-subject-4","notes": "Tech specification"},{"id": "cc-subject-5","notes": "Reference standards"}]}');