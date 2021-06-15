INSERT INTO account(id ,email, enabled, first_name, last_name, locked, password)
--SilneH@slo123
VALUES (1 ,'','TRUE','Diet','Master','FALSE','$2a$10$NDNulLFJNrekjsuBCQ3YB.RFdbvUjI4v.AVZMyYSRSuTyQhzxUBWm');

INSERT INTO access_level(level, id, account)
VALUES ('level.admin', 1, 1);

INSERT INTO nutrient (category, description, name)
VALUES
--1
('calories',
 'Metric system unit of energy, still widely used in food-related contexts - calorie, ' ||
 'more precisely, the "food calorie", "large calorie" or kilocalorie (kcal or Cal), equal to 4.184 kilojoules.',
 'Calorie'),

--2
('carbohydrate',
 'The total amount of fully digestible carbohydrates contained within a product or meal',
 'Total Carbohydrate'),

--3
('carbohydrate',
 'Dietary fiber, also known as roughage, is the indigestible part of plant foods. Fiber has a host of ' ||
 'health benefits, including reducing the risk of heart disease and type 2 diabetes.',
 'Dietary Fiber'),

--4
('carbohydrate',
 'Include sugars naturally present in many nutritious foods and beverages, such as sugar in milk and fruits ' ||
 'as well as any added sugars that may be present in the product',
 'Total Sugars'),

--5
('carbohydrate',
 'Include sugars that are added during the processing of foods (such as sucrose or dextrose), foods packaged ' ||
 'as sweeteners (such as table sugar), sugars from syrups and honey, and sugars from concentrated fruit etc.',
 'Added Sugars'),

--6
('mineral',
 'Builds strong bones and teeth and helps in muscle contraction, blood clotting, nerve transmission, cell ' ||
 'signaling and regulation of metabolism. The deficiency of calcium makes bone fragile and easy to fracture.',
 'Calcium'),

--7
('mineral',
 'Helps in muscle contraction, conducts nerve impulses and controls the fluid balance in the body. ' ||
 'The primary source of dietary sodium is table salt.',
 'Sodium'),

--8
('mineral',
 'Plays a crucial role in maintaining fluid balance, muscle contraction and nerve impulse conduction. It ' ||
 'supports brain health and reduces the risk of stroke. Low potassium causes irregular heartbeats, edema etc.',
 'Potassium'),

--9
('mineral',
 'In association with sodium maintains the normal fluid balance in the body. It is used in the ' ||
 'formation of hydrochloric acid (stomach acid) for digestion and to sustain electrical neutrality in the body.',
 'Chloride'),

--10
('mineral',
 'Acts as a cofactor in several enzymatic reactions and is required for the synthesis of deoxyribonucleic ' ||
 'acid (DNA) and an antioxidant, glutathione.',
 'Magnesium'),

--11
('mineral',
 'Helps build and repair bones and teeth, helps nerves function and makes muscles contract. ' ||
 'Phosphorus deficiency leads to bone diseases and growth restriction in children.',
 'Phosphorous'),

--12
('mineral',
 'Mineral used to produce thyroid hormones. It is necessary for the body’s metabolism and physical and ' ||
 'mental development. Phosphorus deficiency leads to impaired growth in children and metabolic disorders.',
 'Iodine'),

--13
('mineral',
 'Used in hemoglobin formation, which carries oxygen in the blood. Iron deficiency can lead to cellular ' ||
 'hypoxia (decreased oxygen) and cell death.',
 'Iron'),

--14
('mineral',
 'Aids in cell division, immunity and wound healing. Low zinc levels impair the immune system. Oysters, ' ||
 'red meat, poultry, beans, nuts and whole grains provide major quantities of zinc.',
 'Zinc'),

--15
('mineral',
 'Helps in energy production and facilitates iron uptake from the gut. Chocolate, liver, ' ||
 'shellfish and wheat bran cereals are rich sources.',
 'Copper'),

--16
('mineral',
 'Plays an important role in protein, carbohydrate and cholesterol breakdown and cell division. Along with ' ||
 'vitamin K, it helps in blood clotting. Whole grains, nuts, soybeans and rice are rich in manganese.',
 'Manganese'),

--17
('mineral',
 'Has antibacterial properties and helps fight acne-causing bacteria in the skin. It also repairs DNA ' ||
 'damage. Seafood and legumes, especially soybeans, black beans and kidney beans are rich sources of sulfur.',
 'Sulfur'),

--18
('mineral',
 'Helps prevent oxidative damage to the cells. It is also very important for the metabolism of the thyroid ' ||
 'hormone. Brazil nuts, seafood and organ meats are good sources of selenium.',
 'Selenium'),

--19
('mineral',
 'Essential mineral found in high concentrations in legumes, grains and organ meats.',
 'Molybdenum'),

--20
('protein',
 'One of the building blocks of body tissue that can also serve as a fuel source. As a fuel, proteins ' ||
 'provide as much energy density as carbohydrates.',
 'Protein'),

--21
('fat',
 'Indicates how much fat is in a single serving of a food. Limit total fat to less than 25% to 35% percent ' ||
 'of the calories you consume each day',
 'Total Fat'),

--22
('fat',
 'A type of fat that is solid at room temperature, can be found in some plant foods (for example, ' ||
 'tropical plant oils like coconut oil), and many animals.',
 'Saturated Fat'),

--23
('fat',
 'Unsaturated fat, but it is structurally different than unsaturated fat that occurs naturally in plant ' ||
 'foods. Tra ns fat has detrimental health effects and is not essential in the diet.',
 'Trans Fat'),

--24
('fat',
 'Cholesterol and its derivatives are important constituents of cell membranes and precursors of other ' ||
 'steroid compounds, but a high proportion in the blood is associated with an increased risk of heart disease.',
 'Cholesterol'),

--25
('vitamin',
 'Involved in a wide range of metabolic processes, both in humans and in other organisms, primarily related ' ||
 'to the utilization of fats, carbohydrates, and amino acids.',
 'Biotin B7'),

--26
('vitamin',
 'Impacts liver function, healthy brain development, muscle movement, your nervous system and metabolism.',
 'Choline B4'),

--27
('vitamin',
 'Converted into folate by the body, is used as a dietary supplement and in food fortification as it is ' ||
 'more stable during processing and storage.',
 'Folic Acid B9'),

--28
('vitamin',
 'Vitamin B3, is an important nutrient. In fact, every part of your body needs it to function properly. As a ' ||
 'supplement, niacin may help lower cholesterol, ease arthritis and boost brain function, among other benefits.',
 'Niacin B3'),

--29
('vitamin',
 'Required in order to synthesize coenzyme A (CoA) – essential for fatty acid metabolism – as well as to in ' ||
 'general synthesize and metabolize proteins, carbohydrates, and fats.',
 'Pantothenic Acid B5'),

--30
('vitamin',
 'Required by the body for cellular respiration. Food sources include eggs, green vegetables, milk and other ' ||
 'dairy products, meat, mushrooms, and almonds. Some countries require its addition to grains.',
 'Riboflavin B2'),

--31
('vitamin',
 'Plays a critical role in energy metabolism and, therefore, in the growth, development, and function of cells.',
 'Thiamine B1'),

--32
('vitamin',
 'Includes retinol, retinal, and several provitamin A carotenoids. It is important for growth and development, ' ||
 'for the maintenance of the immune system, and for good vision.',
 'Vitamin A'),

--33
('vitamin',
 'It’s significant to protein, fat and carbohydrate metabolism and the creation of red blood cells and ' ||
 'neurotransmitters.',
 'Vitamin B6'),

--34
('vitamin',
 'Particularly important in the normal functioning of the nervous system via its role in the synthesis of ' ||
 'myelin, and in the maturation of developing red blood cells in the bone marrow.',
 'Vitamin B12'),

--35
('vitamin',
 'An essential nutrient involved in the repair of tissue and the enzymatic production of certain ' ||
 'neurotransmitters. It is required for the functioning of several enzymes and is important for immune system.',
 'Vitamin C'),

--36
('vitamin',
 'Responsible for increasing intestinal absorption of calcium, magnesium, and phosphate, and many other ' ||
 'biological effects.',
 'Vitamin D'),

--38
('vitamin',
 'Its main role is to act as an antioxidant, scavenging loose electrons—so-called ' ||
 '“free radicals”—that can damage cells.',
 'Vitamin E'),

--39
('vitamin',
 'The human body requires vitamin K for post-synthesis modification of certain proteins that are required for' ||
 ' blood coagulation or for controlling binding of calcium in bones and other tissues.',
 'Vitamin K');

INSERT INTO diet
VALUES
(1, 'public', 'Wysokobiałkowa dieta, stworzona z myślą o sportowcach, którzy chcą zwiększyć swoją masę mięśniową', 'Dieta SPORT Pudzian', 'raw_food', 1),
(2, 'public', 'Tanio i smacznie, dzięki tej diecie możesz powiększyć swoje oszczędności', 'Studencka dieta - na każdą kieszeń', 'vegetarian', 1),
(3, 'public', 'Śródziemnomorska dieta, której stosowanie niesie ogrom korzyści zdrowotnych jak i wyjątkowych doznań smakowych', 'Dieta Apenińska', 'mediterranean', 1),
(4, 'public', 'Dieta, która wprowadzi Cię w stan ketozy, dzięki odpowiednio niskiej podaży węglowodanów', 'Dieta Ketogeniczna', 'low_carb', 1);

INSERT INTO meal(id, description, name, type, containing_diet)
VALUES
--types: breakfast second_breakfast elevenses brunch lunch tea dinner supper

--dieta sport
(1, 'Szybki, wysokobiałkowy posiłek na dobry początek dnia', 'Jajecznica z bekonem', 'breakfast', 1),
(2, 'Posiłek przedtreningowy na uzupełnienie enrgii', 'Owsianka z bananem i orzechami', 'second_breakfast', 1),
(3, 'Główny posiłek w ciągu dnia', 'stek wołowy z kaszą i surówką', 'lunch', 1),
(4, 'Słodka kolacja bogata w białko', 'Pudding twarogowy', 'dinner', 1),

--studencka dieta
(5, 'Śniadanie mistrzów', 'Owsianka na mleku', 'breakfast', 2),
(6, 'Dużo witamin i minerałów w niskiej cenie', 'Potrawka warzywna', 'lunch', 2),
(7, 'Prosta i smaczna kolacja na koniec dnia', 'Tosty z serem', 'dinner', 2),

--Dieta Apenińska
(8, 'Jajecznica z dodatkami typowami dla śródziemnomorskiego klimatu', 'Jajecznica z suszonymi pomidorami i czerwona papryką', 'breakfast', 3),
(9, 'Słodka przekąska na drugą połowę dnia', 'Jogurt naturalny z gruszkami i bananem', 'second_breakfast', 3),
(10, 'To z czego Apeniny słyną najbardziej', 'Łosoś grillowany z suszonymi pomidorami i kaszą z surówką', 'lunch', 3),
(11, 'Orzechowa przekąska', 'Orzechy włoske w sałatce marchewkowej', 'dinner', 3),

--Dieta keto
(12, 'Jajeczne szaleństwo', 'Jajka na twardo zapiekane w jajku', 'breakfast', 4),
(13, 'Proste, lecz wykwintne danie bez węglowodanów', 'Tatar wołowy z orzechami', 'lunch', 4),
(14, 'Słodka kolacja bogata w białko', 'Pudding twarogowy', 'dinner', 4);

INSERT INTO ingredient (id, category, description, name)
VALUES
(1, 'egg', 'Hard-boiled eggs are loaded with nutrients, protein and healthy fats.', 'Hard-boiled Egg'),
(2,'meat','Beef is primarily composed of protein and varying amounts of fat.','Beef'),
(3,'vegetable','Carrots’ water content ranges from 86–95%, and the edible portion consists of around 10% carbs. Carrots ' ||'contain very little fat and protein.','Carrot'),
(4,'fruit','Pears come in many different varieties. Bartlett, Bosc, and D’Anjou pears are among the most popular, ' ||'but around 100 types are grown worldwide.','Pear'),
(5,'dairy','The nutritional profile of milk is impressive. After all, it’s designed to fully nourish newborn animals.','Cow Milk'),
(6,'egg','Eggs are a protein and nutrient powerhouse that can be added to many dishes and prepared in numerous ways','Egg'),
(7,'meat','Bacon typically goes through a curing process, during which the meat is soaked in a solution of salt, nitrates and sometimes sugar. In most cases, the bacon is smoked afterward','Bacon'),
(8,'grain','The nutrient composition of oats is well-balanced. They are a good source of carbs and fiber, including the powerful fiber beta-glucan','Oatmeal'),
(9,'fruit','Bananas Contain Many Important Nutrients. Bananas are among the world''s most popular fruits.','Banana'),
(10,'nut','To say that walnuts are a nutritious food is a bit of an understatement.Walnuts provide healthy fats, fiber, vitamins and minerals','Walnuts'),
(11,'grain','Buckwheat has become popular as a health food due to its high mineral and antioxidant content. Its benefits may include improved blood sugar control.','Buckwheat'),
(12,'vegetable','Cultivars of the plant produce fruits in different colors, including red, yellow, orange, green, white, and purple. Bell peppers are sometimes grouped with less pungent pepper varieties as "sweet peppers"','Pepper'),
(13,'grain','Bread is a staple food prepared from a dough of flour and water, usually by baking. Throughout recorded history, it has been a prominent food in large parts of the world. It is one of the oldest human-made foods','Bread'),
(14,'dairy','Cheese is a dairy product, derived from milk and produced in wide ranges of flavors, textures and forms by coagulation of the milk protein casein. It comprises proteins and fat from milk, usually the milk of cows, buffalo, goats, or sheep.','Cheese'),
(15,'fish','Salmon is one of the most nutritious foods on the planet. This popular fatty fish is loaded with nutrients and may reduce risk factors for several diseases. It’s also tasty, versatile and widely available.','Salmon'),
(16, 'dairy', 'Quark or quarg is a type of fresh dairy product made by warming soured milk until the desired amount of curdling is met, and then straining it', 'Quark'),
(17, 'vegetable', 'The tomato is the edible berry of the plant Solanum lycopersicum, commonly known as a tomato plant.', 'Tomato'),
(18, 'dairy', 'Yogurt is a food produced by bacterial fermentation of milk.[2] The bacteria used to make yogurt are known as yogurt cultures.', 'Yogurt');
INSERT INTO ingredient_nutrition (per100, ingredient, nutrient)
VALUES
-- Hard-boiled Egg
        (154, 1, 1),    -- Calories
        (10, 1, 21),    -- Total Fat
        (3, 1, 22),     -- Saturated Fat
        (0, 1, 23),     -- Trans Fat
        (0.37, 1, 24),  -- Cholesterol
        (1.4, 1, 7),    -- Sodium
        (0, 1, 2),      -- Total Carbohydrate
        (0, 1, 3),      -- Dietary Fiber
        (0, 1, 4),      -- Total Sugars
        (0, 1, 5),      -- Added Sugars
        (12, 1, 20),    -- Protein
        -- VITAMINS (in micro grams)
        (2, 1, 36),     -- Vitamin D
        (56, 1, 6),     -- Calcium
        (2000, 1, 13),  -- Iron
        (138000, 1, 8), -- Potassium
        (400, 1, 30),   -- Riboflavin B2
        (0.8, 1, 34),   -- Vitamin B12
        (20, 1, 25),    -- Biotin
        (2000, 1, 29),  -- Pantheonic Acid
        (54, 1, 12),    -- Iodine
        (2000, 1, 14),  -- Zinc
        (30, 1, 18),    -- Selenium
        (16, 1, 19),    -- Molybdenum
        (294000, 1, 26),-- Choline
-- Beef
        (291, 2, 1),   -- Calories
       (20, 2, 21),   -- Total Fat
       (7.8, 2, 22),  -- Saturated Fat
       (0, 2, 23),    -- Trans Fat
       (0.87, 2, 24), -- Cholesterol
       (0.63, 2, 7),  -- Sodium
       (0, 2, 2),     -- Total Carbohydrate
       (0, 2, 3),     -- Dietary Fiber
       (0, 2, 4),     -- Total Sugars
       (0, 2, 5),     -- Added Sugars
       (26, 2, 20),   -- Protein
-- VITAMINS (in micro grams)
       (7000, 2, 6),  -- Calcium
       (2700, 2, 13), -- Iron
       (319000, 2, 8),-- Potassium

-- Carrot
        (41, 3, 1),     -- Calories
       (0.24, 3, 21),  -- Total Fat
       (0, 3, 22),     -- Saturated Fat
       (0, 3, 23),     -- Trans Fat
       (0, 3, 24),     -- Cholesterol
       (0.69, 3, 7),   -- Sodium
       (9.58, 3, 2),   -- Total Carbohydrate
       (2.8, 3, 3),    -- Dietary Fiber
       (4.54, 3, 4),   -- Total Sugars
       (0, 3, 5),      -- Added Sugars
       (0.93, 3, 20),  -- Protein
-- VITAMINS (in micro grams)
       (33000, 3, 6),  -- Calcium
       (300, 3, 13),   -- Iron
       (320000, 3, 8), -- Potassium
       (841, 3, 32),   -- Vitamin A
       (5900, 3, 35), -- Vitamin C

-- Pear
        (58, 4, 1),     -- Calories
       (0.12, 4, 21),  -- Total Fat
       (0.01, 4, 22),  -- Saturated Fat
       (0, 4, 23),     -- Trans Fat
       (0, 4, 24),     -- Cholesterol
       (0.01, 4, 7),   -- Sodium
       (15.46, 4, 2),  -- Total Carbohydrate
       (3.1, 4, 3),    -- Dietary Fiber
       (9.8, 4, 4),    -- Total Sugars
       (0, 4, 5),      -- Added Sugars
       (0.38, 4, 20),  -- Protein
-- VITAMINS (in micro grams)
       (9000, 4, 6),   -- Calcium
       (170, 4, 13),   -- Iron
       (238000, 4, 8), -- Potassium
       (1, 4, 32),     -- Vitamin A
       (8400, 4, 35), -- Vitamin C
-- Cow Milk
        (60, 5, 1),     -- Calories
       (3.25, 5, 21),  -- Total Fat
       (1.865, 5, 22),  -- Saturated Fat
       (0, 5, 23),     -- Trans Fat
       (0.01, 5, 24),     -- Cholesterol
       (0.01, 5, 7),   -- Sodium
       (4.52, 5, 2),  -- Total Carbohydrate
       (0, 5, 3),    -- Dietary Fiber
       (5.26, 5, 4),    -- Total Sugars
       (0, 5, 5),      -- Added Sugars
       (3.22, 5, 20),  -- Protein
-- VITAMINS (in micro grams)
       (113000, 5, 6),   -- Calcium
       (30, 5, 13),   -- Iron
       (143000, 5, 8), -- Potassium
       (28, 5, 32),     -- Vitamin A
       (0, 5, 35), -- Vitamin C
--egg
       (154, 6, 1),    -- Calories
       (10, 6, 21),    -- Total Fat
       (3, 6, 22),     -- Saturated Fat
       (0.37, 6, 24),  -- Cholesterol
       (1.4, 6, 7),    -- Sodium
       (12, 6, 20),    -- Protein
--bacon
       (540,7, 1),    -- Calories
       (42, 7, 21),    -- Total Fat
       (110, 7, 24),  -- Cholesterol
       (1717, 7, 7),    -- Sodium
       (565, 7, 8),    -- Potassium
       (1.4, 7, 2),    -- Carbo
       (37, 7, 20),    -- Protein
--oatmeal
       (373,8, 1),    -- Calories
       (60, 8, 2),    -- Carbo
       (6.6, 8, 21),    -- Total Fat
       (13.5, 8, 20),    -- Protein
       (8, 8, 3),    -- Fiber
--banana
       (88,9, 1),    -- Calories
       (23, 9, 2),    -- Carbo
       (0.3, 9, 21),    -- Total Fat
       (1.1, 9, 20),    -- Protein
       (2.3, 9, 3),    -- Fiber
       (358, 9, 8),    -- Potassium
--Walnuts
       (654,  10, 1),    -- Calories
       (14,  10, 2),    -- Carbo
       (65, 10, 21),    -- Total Fat
       (15, 10, 20),    -- Protein
       (7, 10, 3),    -- Fiber
       (441, 10, 8),    -- Potassium
--Buckwheat
       (359,  11, 1),    -- Calories
       (67,  11, 2),    -- Carbo
       (2.8, 11, 21),    -- Total Fat
       (10, 11, 20),    -- Protein
       (5.9, 11, 3),    -- Fiber
--Pepper
       (30,  12, 1),    -- Calories
       (6,  12, 2),    -- Carbo
       (0.3, 12, 21),    -- Total Fat
       (1, 12, 20),    -- Protein
       (2, 12, 3),    -- Fiber
--Bread
       (264,  13, 1),    -- Calories
       (6,  13, 2),    -- Carbo
       (0.3, 13, 21),    -- Total Fat
       (1, 13, 20),    -- Protein
       (2, 13, 3),    -- Fiber
--cheese
       (356,  14, 1),    -- Calories
       (2.2,  14, 2),    -- Carbo
       (27, 14, 21),    -- Total Fat
       (25, 14, 20),    -- Protein
       (114, 14, 24),  -- Cholesterol
       (121, 14, 8),    -- Potassium
--Salmon
       (142,  15, 1),    -- Calories
       (0,  15, 2),    -- Carbo
       (6.3, 15, 21),    -- Total Fat
       (20, 15, 20),    -- Protein
       (55, 15, 24),  -- Cholesterol
       (490, 15, 8),    -- Potassium
       (35, 15, 18),    -- Selenium
--Quark
    (98,  16, 1),    -- Calories
       (3.5,  16, 2),    -- Carbo
       (4.3, 16, 21),    -- Total Fat
       (13, 16, 20),    -- Protein
--Tomato
        (17,  17, 1),    -- Calories
       (3,  17, 2),    -- Carbo
       (0.2, 17, 21),    -- Total Fat
       (1, 17, 20),    -- Protein
       (237, 17, 8),    -- Potassium
--Yogurt
        (58,  18, 1),    -- Calories
       (3,  18, 2),    -- Carbo
       (0.4, 18, 21),    -- Total Fat
       (10, 18, 20),    -- Protein
       (141, 18, 8);


INSERT INTO meal_ingredient (id, cost, count, grams, ingredient, meal)
VALUES
        (1,1, 6, 400, 6, 1),
        (2,3.5, 4, 150, 7, 1),

        (3,0.3, 1, 120, 8, 2),
        (4,0.25, 1, 250, 5, 2),
        (5,0.7, 1, 120, 9, 2),
        (6,3, 8, 60, 10, 2),

        (7,2.8, 1, 300, 2, 3),
        (8,0.8, 1, 120, 11, 3),
        (9,0.2, 3, 350, 3, 3),

        (10,1.3, 1, 350, 16, 4),
        (11,0.7, 1, 120, 9, 4),

        (12,0.3, 1, 120, 8, 5),
        (13,0.25, 1, 250, 5, 5),

        (14,0.3, 10, 700, 3, 6),
        (15,0.12, 2, 400, 12, 6),
        (16,0.4, 2, 500, 17, 6),

        (17,0.2, 2, 200, 13, 7),
        (18,2, 2, 30, 14, 7),

        (19,2, 3, 180, 6, 8),
        (20,1.5, 2, 500, 17, 8),
        (21,0.2, 2, 200, 12, 8),

        (22,1, 1, 500, 18, 9),
        (23,1, 1, 500, 4, 9),
        (24,0.7, 2, 250, 9, 9),

        (25,6, 2, 300, 15, 10),
        (26,1.5, 5, 500, 17, 10),
        (27,1, 5, 100, 11, 10),
        (28,0.2, 5, 300, 3, 10),

        (29,0.2, 5, 300, 3, 11),
        (30,4, 8, 150, 10, 11),

        (31,1, 4, 200, 1, 12),
        (32,1, 4, 200, 6, 12),

        (33,3.8, 1, 200, 2, 13),
        (34,3, 8, 50, 10, 13),

        (35,1.3, 1, 450, 16, 14),
        (36,0.7, 1, 120, 9, 14);