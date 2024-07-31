USE `local_guide`;
-- SET SESSION SQL_MODE='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS=0; 

DELETE FROM `location`;
DELETE FROM `tour`;	
DELETE FROM `location_tour`;
DELETE FROM `tour_start_time`;
DELETE FROM `image`;
DELETE FROM `language`;
DELETE FROM `level_detail`;
DELETE FROM `language_skill`;
DELETE FROM `user`;
DELETE FROM `user_role`;
DELETE FROM `role`;
DELETE FROM `category`;
DELETE FROM `category_tour`;
DELETE FROM `cart`;
DELETE FROM `booking`;
DELETE FROM `busy_schedule`; 
DELETE FROM `invoice`;
DELETE FROM `review`;
DELETE FROM `traveler_request`;
DELETE FROM `notification`;
DELETE FROM `guide_application`;
DELETE FROM `crypto_payment_detail`;

INSERT INTO `user` (id, address, date_of_birht, email, `password`, phone, full_name, biography, credential, overall_rating)
VALUES 
	(1, 'Ha Noi', '1999-01-01', 'admin12345@gmail.com', '$2a$10$vI9cLxpeCaic7r6X7DNufOE4YoomEtTnam8nGCCZ4wphTsY3bOb9i', '0374561232', 'admin', NULL, NULL, NULL),
	(2, 'Ha Noi', '1998-02-01', 'ta642379@gmail.com', '$2a$10$SZcs.odsQEiTTBYxKxWpreVWeXuP5UCQJZm5Ef4fHDebH5MHC7LT2', '03741236589', 'Nguyen Van Thai', 
		"Welcome to Vietnam! I'm Thai, a seasoned guide from Ha Noi. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 4.7),
	(3, 'Ha Noi', '1997-03-01', 'guide123451@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0769872674', 'Tran Van Hai', 
		"Welcome to Vietnam! I'm Hai, a seasoned guide from Ha Noi. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 4.5),
	(4, 'Ha Noi', '1996-05-01', 'guide123452@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0768421562', 'Ho Nguyen Tan Dat', 
		"Welcome to Vietnam! I'm Dat, a seasoned guide from Ha Noi. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 3),
	(5, 'Ho Chi Minh', '1994-07-01', 'guide123453@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0326579088', 'Dang Thien Binh', 
		"Welcome to Vietnam! I'm Binh, a seasoned guide from Ho Chi Minh. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 2),
	(6, 'Ho Chi Minh', '2000-09-23', 'guide123454@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0345679812', 'Ho Trung Dung', 
		"Welcome to Vietnam! I'm Dung, a seasoned guide from Ho Chi Minh. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 1),
	(7, 'Ho Chi Minh', '2002-10-15', 'guide123455@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0905678145', 'Tran Quoc Thien',
		"Welcome to Vietnam! I'm Thien, a seasoned guide from Ho Chi Minh. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 4.7),
	(8, 'Da Nang', '2001-12-30', 'guide123456@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0905348965', 'Nguyen Van Toan', 
		"Welcome to Vietnam! I'm Toan, a seasoned guide from Da Nang. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL, 4.7),
	(9, 'Da Nang', '1990-06-10', 'guide123457@gmail.com', '$2a$10$fPMW5HHxaf5/54.Gs2I0O.vdDcPUwOaZ5TmVVCx50xhGMyqJbspRC', '0976854245', 'Do Tan Dat', 
		"Welcome to Vietnam! I'm Dat, a seasoned guide from Da Nang. With over 13 years experience, I offer personalized tours showcasing the rich culture and history of Hue, Danang, and Hoi An. My licenses ensure legal and quality service. Let's explore Vietnam together!", NULL,  4.7),
	(10, 'Quang Nam', '2002-09-16', 'vanthinh1609@gmail.com', '$2a$10$FHZSImT7Xy51MeU7DKeGb.MKVKjbVUOJ7So2iGr/46iWcVPRqnGLy', '0373228584', 'Tran Van Thinh', NULL, NULL, NULL),
	(11, 'Quang Nam', '2002-01-02', 'nguyenquan.212002@gmail.com', '$2a$10$7XS6UIT8PIkCCGtpic2jVui5AbzLJR3/NkwkkwadmdV4nWJRdYNi2', '0766678406', 'Nguyen Quan', NULL, NULL, NULL),
	(12, 'Da Nang', '2002-04-16', 'lethanhtu164@gmail.com', '$2a$10$dovslbFBJYpcG5HPXIqQ9ObW5gaxUlVR3GeE4n2iv8SkAowdrl9.S', '0935409092', 'Tang Le Thanh Tu', NULL, NULL, NULL),
	(13, 'Hue', '2002-10-01', 'thoathoa0110@gmail.com', '$2a$10$XapwUhWGVTri/MuXFxuLQOjM3jGeyfBVH9pgAuAoliwhcihxr7HTe', '0378537190', 'Truong Thi Thoa', NULL, NULL, NULL);
													
INSERT INTO `user_role` (user_id, role_id)
VALUES 
	(1, 1),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 2),
    (9, 2),
    (10, 3),
    (11, 3),
    (12, 3),
    (13, 3);
    
INSERT INTO `role` (id, `name`)
VALUES 
	(1, 'ADMIN'),
    (2, 'GUIDER'),
    (3, 'TRAVELER');
    
INSERT INTO location (id, name, latitude, longitude, address)
VALUES
	(1 , 'Ba Na hill'						  , '16.02650365766278' , '108.03279337529376', 'Hoa Vang, Da Nang, Vietnam'),
	(2 , 'Banh Xeo Ba Duong'				  , '16.05890304368443' , '108.21616615113433', '280/23 Hoang Dieu, Binh Hien, Hai Chau, Da Nang, Vietnam'),
	(3 , 'Banh Trang Thit Heo Dai Loc 2'      , '16.0589430390772'  , '108.21810975195643', '124 Huynh Thuc Khang, Nam Duong, Hai Chau, Da Nang, Vietnam'),
	(4 , 'Cho Dem Son Tra'					  , '16.061755011005317', '108.2322357729315' , 'Mai Hac De, An Hai Trung, Son Tra, Da Nang, Vietnam'),
	(5 , 'Cong vien APEC'					  , '16.058167450480838', '108.22322379945102', 'Binh Hien, Hai Chau, Da Nang, Vietnam'),
	(6 , 'Bao tang Dieu khac Cham Da Nang'	  , '16.060388333662477', '108.22324350128271', 'So 02 Duong 2 Thang 9, Binh Hien, Hai Chau, Da Nang, Vietnam'),
	(7 , 'Bao tang Ho Chi Minh'				  , '16.04878092798715' , '108.21772762877838', 'Duy Tan, Hoa Cuong Bac, Hai Chau, Da Nang, Vietnam'),
	(8 , 'Bao tang My thuat Da Nang'		  , '16.07135698229827' , '108.2182188667611' , '78 Duong Le Duan, Thach Thang, Hai Chau, Da Nang, Vietnam'),
	(9 , 'Thu vien Khoa hoc tong hop Da Nang' , '16.073883147334314', '108.2235864872382' , '33 Tran Phu, Hai Chau 1, Q. Hai Chau, Da Nang, Vietnam'),
	(10, 'Dao Ky uc Hoi An'					  , '15.87531276443243' , '108.33984592659145', '200 Nguyen Tri Phuong, Cam Chau, Hoi An, Quang Nam, Vietnam'),
	(11, 'Banh Mi Phuong'					  , '15.878814907664262', '108.33249751924129', '2 Phan Chu Trinh, Cam Chau, Hoi An, Quang Nam, Vietnam'),
	(12, 'Chua cau'							  , '15.87714735350692' , '108.32640611645537', '186 Tran Phu, Phuong Minh An, Hoi An, Quang Nam, Vietnam'),
	(13, 'Hoi Quan Phuoc Kien'				  , '15.87759772743169' , '108.33089880820273', '46 Tran Phu, Cam Chau, Hoi An, Quang Nam, Vietnam'),
	(14, 'Bao tang Van hoa Dan gian Hoi An'	  , '15.87643601698146' , '108.32972031463274', '33 Nguyen Thai Hoc, Phuong Minh An, Hoi An, Quang Nam, Vietnam'),
	(15, 'Cho Hoi An'						  , '15.876926245595307', '108.33156465034926', '19 Tran Phu, Cam Chau, Hoi An, Quang Nam, Vietnam'),
	(16, 'Cho dem Hoi An'				      , '15.876082831584693', '108.3263930875817' , 'Duong Bach Dang, Phuong Minh An, Hoi An, Quang Nam, Vietnam'),
	(17, 'Nha co Tan Ky'				 	  , '15.876566971503305', '108.32788189949862', '101 Nguyen Thai Hoc, Phuong Minh An, Hoi An, Quang Nam, Vietnam'),
	(18, 'Ben Tau Du Lich Song Hoai'	 	  , '15.873566061553602', '108.32610382414622', 'Phuong Minh An, Hoi An, Quang Nam, Vietnam'),
	(19, 'The Noodle House - Signature'	 	  , '15.876673511675659', '108.32884404693785', '55 Le Loi, Hoi An, Quang Nam, Vietnam'),
	(20, 'Bao tang Nghe Y Truyen thong Hoi An', '15.87669115255004' , '108.32990945458002', '34 Nguyen Thai Hoc, Phuong Minh An, Hoi An, Quang Nam, Vietnam'),
	(21, 'Gieng may co'						  , '15.87737056830516' , '108.3313180290525' , '01 Tran Phu, Cam Chau, Hoi An, Quang Nam, Vietnam'),
	(22, 'Da Nang'							  , '16.05836028875089' , '108.20163088899513', 'Da Nang, Vietnam'),
	(23, 'Tp. Hoi An'					      , '15.880375656114575', '108.34030590686254', 'Hoi An, Quang Nam, Vietnam'),
	(24, 'Tp. Hue'							  , '16.465556745449277', '107.58809386601746', 'Tp Hue, Thua Thien Hue, Vietnam'),
	(25, 'Ha Noi'							  , '21.027834030820337', '105.83615705390919', 'Ha Noi, Vietnam'),
	(26, 'Nha hat Lon Ha Noi'				  , '21.02622979933381' , '105.86503656598188', '1 Trang Tien, Phan Chu Trinh, Hoan Kiem, Ha Noi, Vietnam'),
	(27, 'Cho Dong Ba'						  , '16.47283046231636' , '107.58872412243122', 'Phu Hoa, Thanh pho Hue, Thua Thien Hue, Vietnam');

INSERT INTO `tour` ( id, `name`, `description`, duration, estimated_local_cash_needed, include_service, itinerary, limit_traveler, overall_rating, price_per_traveler, transportation, unit, guide_id, `status`, is_for_specific_traveler)				
VALUES 
	(1, 'Hiking Adventure'		   			 , 'Explore scenic trails and breathtaking views.'          																   , 3, '500,000 VND', 'Guided tour, Snacks, Water bottle'   			, 'Day 1: Start from Hanoi. Day 2: Hike through the mountains. Day 3: Return to Hanoi.', 5 , 0  , 150.00 , 'Private van, on foot'     , 'day(s)' , 2, 'PENDING', 0),
	(2, 'Cultural Heritage Tour'   			 , 'Discover the rich history and culture of the region.'   																   , 2, '300,000 VND', 'Museum tickets, Cultural performances, Meals'	, 'Day 1: Visit historical sites. Day 2: Explore cultural landmarks.'				   , 10, 0  , 120.00 , 'Public bus'   			  , 'day(s)' , 2, 'PENDING', 0),
	(3, 'Beach Paradise Getaway'          	 , 'Relax on pristine beaches and enjoy water activities.'  																   , 4, '800,000 VND', 'Beachfront accommodation, Water sports equipment', 'Day 1: Arrive at the beach resort. Day 2-4: Enjoy beach activities and relaxation.', 7 , 0  , 130.00 , 'Public bus, private yacht', 'day(s)' , 2, 'PENDING', 0),
    (4, 'DaNang Food Explore'	   			 , 'Discover the city hidden gems on foot.'				  																	   , 2, '150,000 VND', 'Local guide, Walking tour map'					, 'Day 1: Explore historical districts. Day 2: Stroll through parks.'				   , 4 , 0  , 100.00 , 'Private car'	  		  , 'day(s)' , 3, 'PENDING', 0),
	(5, 'Hoi An Experience'		  			 , 'Immerse yourself in the Hoi An Old City.'				  																   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'								   , 8 , 0  , 50.00  , 'On foot'	  			  , 'hour(s)', 3, 'PENDING', 0),
    
    (6, 'Trekking Expedition'	   			 , 'Embark on an adventure through picturesque pathways and awe-inspiring vistas.'		  									   , 3, '500,000 VND', 'Guided tour, Snacks, Water bottle'				, 'Day 1: Start from Hanoi. Day 2: Hike through the mountains. Day 3: Return to Hanoi.', 3 , 4.4, 150.00 , 'Private van, on foot'     , 'day(s)' , 4, 'ACCEPT' , 0),
	(7, 'Journey through Cultural Heritage'  , 'Explore the vibrant past and cultural heritage of the area.' 															   , 2, '300,000 VND', 'Museum tickets, Cultural performances, Meals'	, 'Day 1: Visit historical sites. Day 2: Explore cultural landmarks.'				   , 6 , 4.5, 120.00 , 'Public bus'   			  , 'day(s)' , 4, 'ACCEPT' , 0),
	(8, 'Coastal Haven Retreat' 			 , 'Unwind on untouched shorelines while indulging in various aquatic pursuits.'											   , 4, '800,000 VND', 'Beachfront accommodation, Water sports equipment', 'Day 1: Arrive at the beach resort. Day 2-4: Enjoy beach activities and relaxation.', 9 , 4.6, 150.00 , 'Public bus, private yacht', 'day(s)' , 4, 'ACCEPT' , 0),
    (9, 'Culinary Adventure in Da Nang'      , 'Explore the lesser-known treasures of the city by wandering on foot.'				  									   , 2, '150,000 VND', 'Local guide, Walking tour map'					, 'Day 1: Explore historical districts. Day 2: Stroll through parks.'                  , 2 , 4.3, 125.00 , 'Private car'      		  , 'day(s)' , 5, 'ACCEPT' , 0),
	(10, 'Immersive Hoi An Encounter'     	 , 'Engage fully with the timeless allure of Hoi An Old City.'			  													   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'                                   , 7 , 4.7, 90.00  , 'On foot'      			  , 'hour(s)', 5, 'ACCEPT' , 0),
     
    (11, 'Trailblazer Journey'	   			 , 'Venture forth along enchanting routes adorned with stunning panoramas and captivating landscapes.'		  				   , 3, '500,000 VND', 'Guided tour, Snacks, Water bottle'				, 'Day 1: Start from Hanoi. Day 2: Hike through the mountains. Day 3: Return to Hanoi.', 9 , 4.4, 150.00 , 'Private van, on foot'  	  , 'day(s)' , 6, 'ACCEPT' , 0),
	(12, 'Exploring the Cultural Legacy'	 , 'Uncover the deep-rooted history and diverse culture of the region.' 													   , 2, '300,000 VND', 'Museum tickets, Cultural performances, Meals'	, 'Day 1: Visit historical sites. Day 2: Explore cultural landmarks.'                  , 15, 4.5, 120.00 , 'Public bus'   			  , 'day(s)' , 7, 'ACCEPT' , 0),
	(13, 'Seaside Escape Getaway'			 , 'Take it easy on immaculate coastlines while engaging in water-based adventures.'										   , 4, '800,000 VND', 'Beachfront accommodation, Water sports equipment', 'Day 1: Arrive at the beach resort. Day 2-4: Enjoy beach activities and relaxation.', 10, 4.6, 135.00 , 'Public bus, private yacht', 'day(s)' , 8, 'ACCEPT' , 0),
    (14, 'Delving into Da Nang Food Scene'   , 'Uncover the hidden jewels of the urban landscape through walking adventures.'				  							   , 2, '150,000 VND', 'Local guide, Walking tour map'					, 'Day 1: Explore historical districts. Day 2: Stroll through parks.'                  , 4 , 4.3, 115.00 , 'Private car'     		  , 'day(s)' , 9, 'ACCEPT' , 0),
	(15, 'Journey into Hoi An Charm'     	 , 'Completely absorb yourself in the magic of Hoi An ancient urban center.'			  									   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'                                   , 4 , 4.7, 80.00 , 'On foot'      			  , 'hour(s)', 9, 'ACCEPT' , 0),
     
	(16, 'Wilderness Wanderlust'      		 , 'Discover the beauty of nature as you traverse through winding paths offering magnificent sights and breathtaking scenery.' , 3, '500,000 VND', 'Guided tour, Snacks, Water bottle'				, 'Day 1: Start from Hanoi. Day 2: Hike through the mountains. Day 3: Return to Hanoi.', 3 , 4.4, 150.00 , 'Private van, on foot'  	  , 'day(s)' , 2, 'ACCEPT' , 0),
	(17, 'Heritage Expedition'				 , 'Delve into the extensive historical background and cultural richness of the locale.' 									   , 2, '300,000 VND', 'Museum tickets, Cultural performances, Meals'	, 'Day 1: Visit historical sites. Day 2: Explore cultural landmarks.'                  , 4 , 4.5, 128.00, 'Public bus'   			  , 'day(s)' , 2, 'ACCEPT' , 0),
	(18, 'Beach Bliss Retreat'				 , 'Recline on unspoiled beaches and partake in an array of water-related leisure activities.'								   , 4, '800,000 VND', 'Beachfront accommodation, Water sports equipment', 'Day 1: Arrive at the beach resort. Day 2-4: Enjoy beach activities and relaxation.', 7 , 4.6, 119.00 , 'Public bus, private yacht', 'day(s)' , 2, 'ACCEPT' , 0),
	(19, 'Gastronomic Exploration of Da Nang', 'Embark on a journey of discovery through the city secret gems by strolling around.'     		  						   , 2, '150,000 VND', 'Local guide, Walking tour map'					, 'Day 1: Explore historical districts. Day 2: Stroll through parks.'                  , 6 , 4.3, 129.00 , 'Private car'      		  , 'day(s)' , 3, 'ACCEPT' , 0),
	(20, 'Exploring the Essence of Hoi An'   , 'Fully immerse yourself in the historical and cultural marvels of Hoi An Old Quarter.'		      						   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'                                   , 5 , 4.7, 79.00 , 'On foot'     			  , 'hour(s)', 3, 'ACCEPT' , 0),

	(21, 'Outdoor Odyssey'      			 , 'Journey through idyllic trails that unveil stunning natural vistas and panoramic views waiting to be explored.'		  	   , 3, '500,000 VND', 'Guided tour, Snacks, Water bottle'				, 'Day 1: Start from Hanoi. Day 2: Hike through the mountains. Day 3: Return to Hanoi.', 3 , 4.4, 149.00 , 'Private van, on foot' 	  , 'day(s)' , 4, 'ACCEPT' , 0),
	(22, 'Tour of Cultural Legacy'			 , 'Learn about the abundant history and cultural tapestry of this particular area.' 										   , 2, '300,000 VND', 'Museum tickets, Cultural performances, Meals'	, 'Day 1: Visit historical sites. Day 2: Explore cultural landmarks.'                  , 4 , 4.5, 120.00 , 'Public bus'  			  , 'day(s)' , 4, 'ACCEPT' , 0),
	(23, 'Tropical Beach Getaway'			 , 'Chill out on secluded sandy shores and relish in a plethora of water-based fun.'										   , 4, '800,000 VND', 'Beachfront accommodation, Water sports equipment', 'Day 1: Arrive at the beach resort. Day 2-4: Enjoy beach activities and relaxation.', 5 , 4.6, 139.00 , 'Public bus, private yacht', 'day(s)' , 4, 'ACCEPT' , 0),
	(24, 'Sampling Da Nang Culinary Delights', 'Traverse the city hidden treasures by exploring on foot.'      		  													   , 2, '150,000 VND', 'Local guide, Walking tour map'					, 'Day 1: Explore historical districts. Day 2: Stroll through parks.'                  , 6 , 4.3, 109.00 , 'Private car'     		  , 'day(s)' , 5, 'ACCEPT' , 0),
	(25, 'Cultural Discovery in Hoi An'      , 'Wholeheartedly embrace the experience of exploring Hoi An Old Town.' 			  										   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'                                   , 9 , 4.7, 69.00 , 'On foot'	 			  , 'hour(s)', 5, 'ACCEPT' , 0),

	(26, 'Nature Quest'      				 , 'Set out on a quest to uncover the beauty of the outdoors, where every trail leads to scenic wonders and breathtaking views', 3, '500,000 VND', 'Guided tour, Snacks, Water bottle'				, 'Day 1: Start from Hanoi. Day 2: Hike through the mountains. Day 3: Return to Hanoi.', 9 , 4.4, 110.00 , 'Private van, on foot' 	  , 'day(s)' , 6, 'ACCEPT' , 0),
	(27, 'Expedition into Cultural Heritage' , 'Immerse yourself in the fascinating history and cultural diversity of the region.' 										   , 2, '300,000 VND', 'Museum tickets, Cultural performances, Meals'	, 'Day 1: Visit historical sites. Day 2: Explore cultural landmarks.'                  , 8 , 4.5, 130.00 , 'Public bus'  			  , 'day(s)' , 7, 'ACCEPT' , 0),
	(28, 'Coastal Paradise Escape'			 , 'Kick back on pristine beachfronts and savor a variety of water-based recreational options.'								   , 4, '800,000 VND', 'Beachfront accommodation, Water sports equipment', 'Day 1: Arrive at the beach resort. Day 2-4: Enjoy beach activities and relaxation.', 7 , 0  , 140.00 , 'Public bus, private yacht', 'day(s)' , 8, 'DENY'   , 0),
	(29, 'Discovering Da Nang Food Culture'  , 'Roam the streets to find the undiscovered gems nestled within the city.'			      								   , 2, '150,000 VND', 'Local guide, Walking tour map'					, 'Day 1: Explore historical districts. Day 2: Stroll through parks.'                  , 6 , 0  , 115.00 , 'Private car'   		      , 'day(s)' , 9, 'DENY'   , 0),
	(30, 'Delving into Hoi An Richness'      , 'Allow yourself to be completely captivated by the essence of Hoi An Old City.'			  								   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'                                   , 5 , 0  , 137.00 , 'On foot'     			  , 'hour(s)', 9, 'DENY'   , 0),
    (31, 'Hoi An Traveler request' 			 , 'Dive into the cultural wonders of Hoi An ancient quarter.'	   		  													   , 3, '50,000 VND' , 'Local guide, Street food tasting'				, 'Explore vibrant stalls and try local delicacies.'                                   , 4 , 0  , 75.00  , 'On foot'   			      , 'hour(s)', 5, 'ACCEPT' , 1);
    
INSERT INTO `location_tour` (location_id, tour_id)
VALUES 
	(1, 1),
	(2, 1),
	(3, 1),
	(4, 1),
	(5, 2),
	(6, 2),
	(7, 2),
	(8, 2),
	(9, 3),
	(10, 3),
	(11, 3),
	(12, 3),
	(13, 4),
	(14, 4),
	(15, 4),
	(16, 4),
	(23, 5),
	(17, 5),
	(18, 5),
	(19, 5),
	(20, 5),
	(21, 6),
	(1, 6),
	(2, 6),
	(3, 6),
	(4, 7),
	(5, 7),
	(6, 7),
	(7, 7),
	(8, 8),
	(9, 8),
	(10, 8),
	(11, 8),
	(12, 9),
	(13, 9),
	(14, 9),
	(15, 9),
	(23, 10),
	(16, 10),
	(17, 10),
	(18, 10),
	(19, 10),
	(20, 11),
	(21, 11),
	(1, 11),
	(2, 11),
	(3, 12),
	(4, 12),
	(5, 12),
	(6, 12),
	(7, 13),
	(8, 13),
	(9, 13),
	(10, 13),
	(11, 14),
	(12, 14),
	(13, 14),
	(14, 14),
	(23, 15),
	(15, 15),
	(16, 15),
	(17, 15),
	(18, 15),
	(1, 16),
	(2, 16),
	(3, 16),
	(4, 16),
	(5, 17),
	(6, 17),
	(7, 17),
	(8, 17),
	(9, 18),
	(10, 18),
	(11, 18),
	(12, 18),
	(13, 19),
	(14, 19),
	(15, 19),
	(16, 19),
	(23, 20),
	(17, 20),
	(18, 20),
	(19, 20),
	(20, 20),
	(21,21),
	(1, 21),
	(2, 21),
	(3, 21),
	(4, 22),
	(5, 22),
	(6, 22),
	(7, 22 ),
	(8, 23),
	(9, 23),
	(10, 23),
	(11, 23),
	(12, 24),
	(13, 24),
	(14, 24),
	(15, 24),
	(23, 25),
	(16, 25),
	(17, 25),
	(18, 25),
	(19, 25),
	(20, 26),
	(21, 26),
	(1, 26),
	(2, 26),
	(3, 27),
	(4, 27),
	(5, 27),
	(6, 27),
	(7, 28),
	(8, 28),
	(9, 28),
	(10, 28),
	(11, 29),
	(12, 29),
	(13, 29),
	(14, 29),
	(23, 30),
	(15, 30),
	(16, 30),
	(17, 30),
	(18, 30),
    (23, 31),
	(16, 31),
	(17, 31);
    

INSERT INTO `tour_start_time` (id, start_time, tour_id)
VALUES 
	(1, '08:00:00', 5),
    (2, '12:00:00', 5),
    (3, '16:00:00', 5),
    
    (4, '07:00:00', 10),
    (5, '11:00:00', 10),
    (6, '15:00:00', 10),

    (7, '09:00:00', 15),
    (8, '13:00:00', 15),
    (9, '17:00:00', 15),
    
    (10, '07:00:00', 20),
    (11, '11:00:00', 20),
    (12, '15:00:00', 20),
    
    (13, '08:00:00', 25),
    (14, '12:00:00', 25),
    (15, '16:00:00', 25),
    
    (16, '09:00:00', 30),
    (17, '13:00:00', 30),
    (18, '17:00:00', 30),
    
    (19, '08:00:00', 1),
    (20, '12:00:00', 2),
    (21, '16:00:00', 3),
    
    (22, '07:00:00', 4),
    (23, '11:00:00', 6),
    (24, '15:00:00', 7),

    (25, '09:00:00', 8),
    (26, '13:00:00', 9),
    (27, '17:00:00', 11),
    
    (28, '07:00:00', 12),
    (29, '11:00:00', 13),
    (30, '15:00:00', 14),
    
    (31, '08:00:00', 16),
    (32, '12:00:00', 17),
    (33, '16:00:00', 18),
    
    (34, '09:00:00', 19),
    (35, '13:00:00', 21),
    (36, '17:00:00', 22),
    
    (37, '07:00:00', 23),
    (38, '11:00:00', 24),
    (39, '15:00:00', 26),
    
    (40, '08:00:00', 27),
    (41, '12:00:00', 28),
    (42, '16:00:00', 29),
    (43, '08:00:00', 31);
    
INSERT INTO `image` (id, associate_name, image_link, associate_id, province_name)
VALUES 
	(1, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959469/location/thytkjs6advsmgodmtga.jpg', 4, NULL),
	(2, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959469/location/nibeddcttgpbyw0ofyqy.jpg', 23, NULL),
	(3, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959469/location/yzyxkk96jcc3xxkncdc7.png', 9, NULL),
	(4, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959469/location/skpx15rfc7izfh4eeuds.jpg', 24, NULL),
	(5, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959469/location/njgkgnqrdlm5eva64qke.jpg', 12, NULL),
	(6, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959468/location/piqfc4yjdxpq8inqvugw.jpg', 27, NULL),
	(7, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959468/location/uz2gxjzeqjsciylpqxim.jpg', 15, NULL),
	(8, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959468/location/yitudidv8jt8ts7qxhsa.jpg', 16, NULL),
	(9, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959467/location/yl61sjx4vsormya8dapq.jpg', 18, NULL),
	(10, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959467/location/kddfb0dpl4jpchae1u6q.jpg', 19, NULL),
	(11, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959467/location/rcg2pbrgrzz87x0mbybb.jpg', 26, NULL),
	(12, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959467/location/qfaxcgp7glqofoiotaik.jpg', 14, NULL),
	(13, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959467/location/h0kgdqf4vxzuormzcs1q.jpg', 8, NULL),
	(14, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/pt6c4dyfkpj9rud0xigj.jpg', 20, NULL),
	(15, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/ucwk9p5r8l83ezwndf6w.jpg', 17, NULL),
	(16, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/vo6hozvw4crea4stqa93.jpg', 13, NULL),
	(17, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/epl0essf58s9zl6vhzxm.jpg', 7, NULL),
	(18, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/ysfbywf10957wpjixbji.jpg', 22, NULL),
	(19, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/znlbf8rpgiisknke9g47.jpg', 25, NULL),
	(20, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/coz50hq95u0wjlimcnwr.jpg', 10, NULL),
	(21, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/svwc1nzyq3metgkonoor.jpg', 21, NULL),
	(22, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/rfhingkmhjed6r0zy7l5.jpg', 6, NULL),
	(23, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959466/location/tsqlcfjysmz2migkp21i.jpg', 2, NULL),
	(24, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959465/location/ikfwwiobnutxbklhegea.jpg', 5, NULL),
	(25, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959465/location/iqxjcfrqy4gqeyrikaho.jpg', 3, NULL),
	(26, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959465/location/bymdggzoazuii6ze0lh9.jpg', 11, NULL),
	(27, 'location', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706959465/location/dldwdfefhz9nb3vzfmnt.jpg', 1, NULL),

	(28, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/ahhs1xzxcvqrycrrhwvh', 1, NULL),
	(29, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/x1j6yabgfrft1ukdy7lx', 1, NULL),
	(30, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/fvjhtbibyqer9cipfnof', 1, NULL),
	(31, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/hp8fdzxf2mjowcujx4kl', 1, NULL),
	(32, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/sxoj0buwwcozchndnm5t', 1, NULL),

	(33, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/xkhnerkofto7uyabt1na.jpg', 2, NULL),
	(34, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/x4l12wkif8rw5ny1ofml.jpg', 2, NULL),
	(35, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/wiw2jpt8zou6nr3zwp0r.jpg', 2, NULL),
	(36, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/hh2q3aevgu5ivhkvtpqm.jpg', 2, NULL),
	(37, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/rwzv6dvzfewstdixib4f.jpg', 2, NULL),

	(38, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/cyrxj0r4ffk0spgqt14c.jpg', 3, NULL),
	(39, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/l1xlquv8l522iuxrlkhe.jpg', 3, NULL),
	(40, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/huucwplly27oqwufejqw.jpg', 3, NULL),
	(41, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/b2hwodo3ehn9zqaylsez.jpg', 3, NULL),
	(42, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/cxnehuzsgbr9xlcxvtqu.jpg', 3, NULL),

	(43, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971268/tour/mcpdvxakvlyjvrtljjut.jpg', 4, NULL),
	(44, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/nkjchzdbxaklozwkpkmc.jpg', 4, NULL),
	(45, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/adhixetfrty8pzdopexo.jpg', 4, NULL),
	(46, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/zrabkh4n8mqjshsqsi25.jpg', 4, NULL),
	(47, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/isucjn5c2ntk3igghgim.jpg', 4, NULL),

	(48, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/b0d76dkhxv1cq9kh9dhe.jpg', 5, NULL),
	(49, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/nhwnavjyqjrin8dgdqrq.jpg', 5, NULL),
	(50, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/j1bvan4cl8kmwfvrmn9m.jpg', 5, NULL),
	(51, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/apmjj3ff3bruzwmdvfoy.jpg', 5, NULL),
	(52, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 5, NULL),

	(53, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/sxoj0buwwcozchndnm5t', 6, NULL),
	(54, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/hp8fdzxf2mjowcujx4kl', 6, NULL),
	(55, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/fvjhtbibyqer9cipfnof', 6, NULL),
	(56, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/x1j6yabgfrft1ukdy7lx', 6, NULL),
	(57, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/ahhs1xzxcvqrycrrhwvh', 6, NULL),

	(58, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/rwzv6dvzfewstdixib4f.jpg', 7, NULL),
	(59, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/hh2q3aevgu5ivhkvtpqm.jpg', 7, NULL),
	(60, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/wiw2jpt8zou6nr3zwp0r.jpg', 7, NULL),
	(61, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/x4l12wkif8rw5ny1ofml.jpg', 7, NULL),
	(62, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/xkhnerkofto7uyabt1na.jpg', 7, NULL),

	(63, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/cxnehuzsgbr9xlcxvtqu.jpg', 8, NULL),
	(64, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/b2hwodo3ehn9zqaylsez.jpg', 8, NULL),
	(65, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/huucwplly27oqwufejqw.jpg', 8, NULL),
	(66, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/l1xlquv8l522iuxrlkhe.jpg', 8, NULL),
	(67, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/cyrxj0r4ffk0spgqt14c.jpg', 8, NULL),

	(68, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/isucjn5c2ntk3igghgim.jpg', 9, NULL),
	(69, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/zrabkh4n8mqjshsqsi25.jpg', 9, NULL),
	(70, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/adhixetfrty8pzdopexo.jpg', 9, NULL),
	(71, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/nkjchzdbxaklozwkpkmc.jpg', 9, NULL),
	(72, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971268/tour/mcpdvxakvlyjvrtljjut.jpg', 9, NULL),

	(73, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 10, NULL),
	(74, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/apmjj3ff3bruzwmdvfoy.jpg', 10, NULL),
	(75, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/j1bvan4cl8kmwfvrmn9m.jpg', 10, NULL),
	(76, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/nhwnavjyqjrin8dgdqrq.jpg', 10, NULL),
	(77, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/b0d76dkhxv1cq9kh9dhe.jpg', 10, NULL),

	(78, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/hp8fdzxf2mjowcujx4kl', 11, NULL),
	(79, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/fvjhtbibyqer9cipfnof', 11, NULL),
	(80, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/x1j6yabgfrft1ukdy7lx', 11, NULL),
	(81, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/ahhs1xzxcvqrycrrhwvh', 11, NULL),
	(82, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/sxoj0buwwcozchndnm5t', 11, NULL),

	(84, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/xkhnerkofto7uyabt1na.jpg', 12, NULL),
	(85, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/x4l12wkif8rw5ny1ofml.jpg', 12, NULL),
	(86, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/wiw2jpt8zou6nr3zwp0r.jpg', 12, NULL),
	(87, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/hh2q3aevgu5ivhkvtpqm.jpg', 12, NULL),
	(88, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/rwzv6dvzfewstdixib4f.jpg', 12, NULL),

	(89, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/b2hwodo3ehn9zqaylsez.jpg', 13, NULL),
	(90, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/huucwplly27oqwufejqw.jpg', 13, NULL),
	(91, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/l1xlquv8l522iuxrlkhe.jpg', 13, NULL),
	(92, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/cyrxj0r4ffk0spgqt14c.jpg', 13, NULL),
	(93, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/cxnehuzsgbr9xlcxvtqu.jpg', 13, NULL),

	(94, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/zrabkh4n8mqjshsqsi25.jpg', 14, NULL),
	(95, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/adhixetfrty8pzdopexo.jpg', 14, NULL),
	(96, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/nkjchzdbxaklozwkpkmc.jpg', 14, NULL),
	(97, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971268/tour/mcpdvxakvlyjvrtljjut.jpg', 14, NULL),
	(98, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/isucjn5c2ntk3igghgim.jpg', 14, NULL),

	(99, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/apmjj3ff3bruzwmdvfoy.jpg', 15, NULL),
	(100, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/j1bvan4cl8kmwfvrmn9m.jpg', 15, NULL),
	(101, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/nhwnavjyqjrin8dgdqrq.jpg', 15, NULL),
	(102, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/b0d76dkhxv1cq9kh9dhe.jpg', 15, NULL),
	(103, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 15, NULL),

	(104, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/fvjhtbibyqer9cipfnof', 16, NULL),
	(105, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/x1j6yabgfrft1ukdy7lx', 16, NULL),
	(106, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/ahhs1xzxcvqrycrrhwvh', 16, NULL),
	(107, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/hp8fdzxf2mjowcujx4kl', 16, NULL),
	(108, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/sxoj0buwwcozchndnm5t', 16, NULL),

	(109, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/wiw2jpt8zou6nr3zwp0r.jpg', 17, NULL),
	(110, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/x4l12wkif8rw5ny1ofml.jpg', 17, NULL),
	(111, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/xkhnerkofto7uyabt1na.jpg', 17, NULL),
	(112, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/hh2q3aevgu5ivhkvtpqm.jpg', 17, NULL),
	(113, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/rwzv6dvzfewstdixib4f.jpg', 17, NULL),

	(114, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/huucwplly27oqwufejqw.jpg', 18, NULL),
	(115, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/l1xlquv8l522iuxrlkhe.jpg', 18, NULL),
	(116, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/cyrxj0r4ffk0spgqt14c.jpg', 18, NULL),
	(117, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/b2hwodo3ehn9zqaylsez.jpg', 18, NULL),
	(118, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/cxnehuzsgbr9xlcxvtqu.jpg', 18, NULL),

	(119, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/adhixetfrty8pzdopexo.jpg', 19, NULL),
	(120, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/nkjchzdbxaklozwkpkmc.jpg', 19, NULL),
	(121, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971268/tour/mcpdvxakvlyjvrtljjut.jpg', 19, NULL),
	(122, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/zrabkh4n8mqjshsqsi25.jpg', 19, NULL),
	(123, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/isucjn5c2ntk3igghgim.jpg', 19, NULL),

	(124, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/j1bvan4cl8kmwfvrmn9m.jpg', 20, NULL),
	(125, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/nhwnavjyqjrin8dgdqrq.jpg', 20, NULL),
	(126, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/b0d76dkhxv1cq9kh9dhe.jpg', 20, NULL),
	(127, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/apmjj3ff3bruzwmdvfoy.jpg', 20, NULL),
	(128, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 20, NULL),

	(129, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/x1j6yabgfrft1ukdy7lx', 21, NULL),
	(130, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/ahhs1xzxcvqrycrrhwvh', 21, NULL),
	(131, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/fvjhtbibyqer9cipfnof', 21, NULL),
	(132, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/hp8fdzxf2mjowcujx4kl', 21, NULL),
	(133, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/sxoj0buwwcozchndnm5t', 21, NULL),

	(134, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/x4l12wkif8rw5ny1ofml.jpg', 22, NULL),
	(135, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/xkhnerkofto7uyabt1na.jpg', 22, NULL),
	(136, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/wiw2jpt8zou6nr3zwp0r.jpg', 22, NULL),
	(137, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/hh2q3aevgu5ivhkvtpqm.jpg', 22, NULL),
	(138, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/rwzv6dvzfewstdixib4f.jpg', 22, NULL),

	(139, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/l1xlquv8l522iuxrlkhe.jpg', 23, NULL),
	(140, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/cyrxj0r4ffk0spgqt14c.jpg', 23, NULL),
	(141, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/huucwplly27oqwufejqw.jpg', 23, NULL),
	(142, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/b2hwodo3ehn9zqaylsez.jpg', 23, NULL),
	(143, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/cxnehuzsgbr9xlcxvtqu.jpg', 23, NULL),

	(144, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/nkjchzdbxaklozwkpkmc.jpg', 24, NULL),
	(145, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971268/tour/mcpdvxakvlyjvrtljjut.jpg', 24, NULL),
	(146, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/adhixetfrty8pzdopexo.jpg', 24, NULL),
	(147, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/zrabkh4n8mqjshsqsi25.jpg', 24, NULL),
	(148, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/isucjn5c2ntk3igghgim.jpg', 24, NULL),

	(149, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/nhwnavjyqjrin8dgdqrq.jpg', 25, NULL),
	(150, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/b0d76dkhxv1cq9kh9dhe.jpg', 25, NULL),
	(151, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/j1bvan4cl8kmwfvrmn9m.jpg', 25, NULL),
	(152, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/apmjj3ff3bruzwmdvfoy.jpg', 25, NULL),
	(153, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 25, NULL),

	-- Not shuffle
	(154, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/ahhs1xzxcvqrycrrhwvh', 26, NULL),
	(155, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/x1j6yabgfrft1ukdy7lx', 26, NULL),
	(156, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/fvjhtbibyqer9cipfnof', 26, NULL),
	(157, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/hp8fdzxf2mjowcujx4kl', 26, NULL),
	(158, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/tour/sxoj0buwwcozchndnm5t', 26, NULL),

	(159, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/hh2q3aevgu5ivhkvtpqm.jpg', 27, NULL),
	(160, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/x4l12wkif8rw5ny1ofml.jpg', 27, NULL),
	(161, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970985/tour/wiw2jpt8zou6nr3zwp0r.jpg', 27, NULL),
	(162, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/xkhnerkofto7uyabt1na.jpg', 27, NULL),
	(163, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706970984/tour/rwzv6dvzfewstdixib4f.jpg', 27, NULL),

	(164, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/cyrxj0r4ffk0spgqt14c.jpg', 28, NULL),
	(165, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/l1xlquv8l522iuxrlkhe.jpg', 28, NULL),
	(166, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971126/tour/huucwplly27oqwufejqw.jpg', 28, NULL),
	(167, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/b2hwodo3ehn9zqaylsez.jpg', 28, NULL),
	(168, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971125/tour/cxnehuzsgbr9xlcxvtqu.jpg', 28, NULL),

	(169, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971268/tour/mcpdvxakvlyjvrtljjut.jpg', 29, NULL),
	(170, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/nkjchzdbxaklozwkpkmc.jpg', 29, NULL),
	(171, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/adhixetfrty8pzdopexo.jpg', 29, NULL),
	(172, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/zrabkh4n8mqjshsqsi25.jpg', 29, NULL),
	(173, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971267/tour/isucjn5c2ntk3igghgim.jpg', 29, NULL),

	(174, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/b0d76dkhxv1cq9kh9dhe.jpg', 30, NULL),
	(175, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971356/tour/nhwnavjyqjrin8dgdqrq.jpg', 30, NULL),
	(176, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/j1bvan4cl8kmwfvrmn9m.jpg', 30, NULL),
	(177, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/apmjj3ff3bruzwmdvfoy.jpg', 30, NULL),
	(178, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 30, NULL),

	(179, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 2, NULL),
	(180, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 3, NULL),
	(181, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 4, NULL),
	(182, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 5, NULL),
	(183, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 6, NULL),
	(184, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 7, NULL),
	(185, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 8, NULL),
	(186, 'user', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1708930178/people/dpj2focyclqiftex7vas.png', 9, NULL),
    
    (187, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/i0b38bo9889kkonnpwgm', NULL, 'An Giang'),
	(188, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/qgrujn7vczehlhlym8xp', NULL, 'Ba Ria Vung Tau'),
	(189, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/if8q5z6obfss8rjqnzjl', NULL, 'Bac Giang'),
	(190, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/lild1rbuufhqtwn1hcpy', NULL, 'Bac Kan'),
	(191, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/bujrx5tbg4efcetcaf40', NULL, 'Bac Lieu'),
	(192, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/zedyxtn8mtc0o0cdedlq', NULL, 'Bac Ninh'),
	(193, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/hlnvr2exzdhjmaxrc4mo', NULL, 'Ben Tre'),
	(194, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/eefdifmpul22hh5pjnoe', NULL, 'Binh Dinh'),
	(195, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/atolsh1g3obec4270adr', NULL, 'Binh Duong'),
	(196, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/n5iurdx4fpfvyy31yivs', NULL, 'Binh Phuoc'),
	(197, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/otnipi3tvescbqxui4k5', NULL, 'Binh Thuan'),
	(198, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/yngd03yrzb1wygdeoqkr', NULL, 'Ca Mau'),
	(199, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/tgglyts0mqfmvx9lfvob', NULL, 'Can Tho'),
	(200, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/da9or5p4yxul891ewfpn', NULL, 'Cao Bang'),
	(201, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/pxaculqbeeavpkvkpl0q', NULL, 'Da Nang'),
	(202, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/kotkvm270jrq5fiehd6s', NULL, 'Dak Lak'),
	(203, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/ckrtsbmbkv3t2eidjjko', NULL, 'Dak Nong'),
	(204, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/lmvxlws7ne8qyo6i3sr2', NULL, 'Dien Bien'),
	(205, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/odze7bbjfmi4brknqtp7', NULL, 'Dong Nai'),
	(206, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/cwbkj0616xqdtyc2ju87', NULL, 'Dong Thap'),
	(207, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/hwm0hkudvjgnt0pn0zvs', NULL, 'Gia Lai'),
	(208, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/kilbloguthx9trixbeke', NULL, 'Ha Giang'),
	(209, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/z59u4ibn7t25udclkfmn', NULL, 'Ha Nam'),
	(210, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/zql523ffo0fxjgasjvsl', NULL, 'Ha Noi'),
	(211, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/ddlzosnz3zlqrxhilmcd', NULL, 'Ha Tay'),
	(212, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/sfhex1virkrksk3is1zb', NULL, 'Ha Tinh'),
	(213, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/grfxzh4yarzfvdcst79y', NULL, 'Hai Duong'),
	(214, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/lgd1srub9iyjecxirckq', NULL, 'Hai Phong'),
	(215, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/kqtmvfs9snrjnl9xea9j', NULL, 'Hau Giang'),
	(216, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/kf3wvqkwsdtv6de0ptiw', NULL, 'Ho Chi Minh'),
	(217, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/jin6p4sxkkzwjeri9zbx', NULL, 'Hoa Binh'),
	(218, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/athxoazamjuxxlezlyrc', NULL, 'Hung Yen'),
	(219, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/crglnpxlrzsc1kh8liwk', NULL, 'Khanh Hoa'),
	(220, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/oa2ujbtiufi8xgthvxuc', NULL, 'Kien Giang'),
	(221, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/rquq7smtlm3g7jpxqckr', NULL, 'Kon Tum'),
	(222, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/sulayua2trrbhgnw0hpg', NULL, 'Lai Chau'),
	(223, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/tjzx2juf9iicrjr1k60r', NULL, 'Lam Dong'),
	(224, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/ifp2tif5qqazjcypsfk5', NULL, 'Lang Son'),
	(225, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/lu8bzl02g2aq4gdzeuxt', NULL, 'Lao Cai'),
	(226, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/axixchbl4nhugwudfgpr', NULL, 'Long An'),
	(227, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/sof9ndjyd9dqbz1l1pxq', NULL, 'Nam Dinh'),
	(228, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/sfuisslew8erzkcyv2w5', NULL, 'Nghe An'),
	(229, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/ynzuw7j8rcathynpmx95', NULL, 'Ninh Binh'),
	(230, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/w9siqfex30hwumchhgqt', NULL, 'Ninh Thuan'),
	(231, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/wfnjrzz2oukvmgn0djqs', NULL, 'Phu Tho'),
	(232, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/pmiutklipzqwy1n4suc8', NULL, 'Phu Yen'),
	(233, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/hyr8nwnrayigfcgbrbok', NULL, 'Quang Binh'),
	(234, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/bwf9hz0l5zsy6xxacvil', NULL, 'Quang Nam'),
	(235, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/li1vylxfa2f0rdgod1qa', NULL, 'Quang Ngai'),
	(236, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/c2354lfvqaruzz690rwa', NULL, 'Quang Ninh'),
	(237, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/gx31obdt5h9gs7g4y5cx', NULL, 'Quang Tri'),
	(238, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/fgdaluh672nt0gwqlvfq', NULL, 'Soc Trang'),
	(239, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/tvdqyyfcawujcikpkdca', NULL, 'Son La'),
	(240, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/mayuapjgybgxcg5pyxx8', NULL, 'Tay Ninh'),
	(241, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/czy3vpgro3sdvs8xdjvy', NULL, 'Thai Binh'),
	(242, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/o0dukwiv4ft6gfv1aryi', NULL, 'Thai Nguyen'),
	(243, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/va2rxbsfmseh47cfbv0n', NULL, 'Thanh Hoa'),
	(244, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/iofe0eocvrkecjpqo21x', NULL, 'Thua Thien Hue'),
	(245, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/ozmdk1ii7begvc2yscae', NULL, 'Tien Giang'),
	(246, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/pi0bjk2wpde7dp9l73pa', NULL, 'Tra Vinh'),
	(247, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/pu7lv3dhyzuskxvs40pe', NULL, 'Tuyen Quang'),
	(248, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/fhpsdx1xipcvljva9yta', NULL, 'Vinh Long'),
	(249, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/zkphvrgw7jskylbvf3g2', NULL, 'Vinh Phuc'),
	(250, 'province', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1/province/cjtadbh4jofwrptk7l6w', NULL, 'Yen Bai'),
    (251, 'tour', 'https://res.cloudinary.com/dh06dk7vs/image/upload/v1706971355/tour/ctpzcbirgdedai4usghc.jpg', 31, NULL);

INSERT INTO `category` (id, `name`)
VALUES 
	(1, 'Days Tours'),
    (2, 'Hours Tours'),
    (3, 'Historical Tours'),
    (4, 'Adventure Tours'),
    (5, 'Cultural Tours'),
    (6, 'Wildlife Tours'),
    (7, 'Culinary Tours'),
    (8, 'Sightseeing');

INSERT INTO `category_tour` (tour_id, category_id)
VALUES 
	(1, 1),
    (2, 1),
    (3, 1),
    (4, 7),
    (5, 2),
	(5, 3),
    (6, 4),
    (7, 5),
    (8, 4),
    (9, 7),
    (10, 2),
    (10, 3),
    (11, 3),
    (12, 3),
    (13, 3),
    (14, 3),
    (15, 2),
    (15, 3),
    (16, 4),
    (17, 4),
    (18, 4),
    (19, 5),
    (20, 2),
    (20, 3),
    (21, 5),
    (22, 5),
    (23, 6),
    (24, 6),
    (25, 2),
    (25, 3),
    (26, 7),
    (27, 7),
    (28, 8),
    (29, 8),
    (30, 2),
    (30, 3);

INSERT INTO `language` (id, `name`)
VALUES 
	(1, 'English'),
	(2, 'Vietnamese'),
    (3, 'Chinese'),
    (4, 'Japanese');
    
INSERT INTO `level_detail` (id, `description`, `level`)
VALUES 
	(1, 'You understand simple phrases and can communicate only basic ideas. These basic ideas include: Greetings, yes, no, simple directions, telling the time. You can express yourself but only to a certain point. You are able to read and write with limited comprehension, and repeat yourself more than once.'												  , 'Basic'),
    (2, 'You are able to better express yourself and give clearer explanations and directions. You can read and understand most information and are able to read it back to the Traveler in a way that he or she can understand. You may have to repeat yourself more than once.'																					  , 'Conversational'),
    (3, 'You can write, read, and speak with very few errors. Communication is clear and you have excellent control over the language. You can translate from your language to the other quickly and you very rarely repeat yourself. You can express yourself and can talk about more abstract things; science, politics, philosophy, history, without any problems.', 'Advanced'),
    (4, 'You have native control of the language. You articulate clearly and effectively, and you do not have to translate between your language and the other. You make few, if any, grammatical mistakes. Linguistic expression comes naturally and you have no problems working in that language.'																  , 'Fluent');

INSERT INTO `language_skill` (id, guide_id, language_id, level_detail_id)
VALUES 
	(1, 2, 1, 3),
    (2, 2, 2, 4),
    (3, 3, 1, 3),
    (4, 3, 3, 3),
    (5, 4, 1, 3),
    (6, 4, 4, 3),
    (7, 5, 2, 4),
    (8, 5, 3, 3),
    (9, 6, 2, 4),
    (10, 6, 4, 3),
    (11, 7, 3, 3),
    (12, 7, 4, 3),
    (13, 8, 1, 3),
    (14, 8, 2, 4),
    (15, 9, 1, 3),
    (16, 9, 3, 3),
    (17, 3, 2, 4),
    (18, 4, 2, 4),
    (19, 7, 2, 4),
    (20, 9, 2, 4);
    
INSERT INTO `cart` (id, traveler_id)
VALUES
	(1, 10),
    (2, 11),
    (3, 12),
    (4, 13);

INSERT INTO `booking` (id, number_travelers, price, start_date, `status`, cart_id, invoice_id, tour_id)
VALUES
-- 	(1 , 1, 200.00 , CONCAT(CURDATE() + INTERVAL 2   day, ' 11:00:00'), 'PENDING_PAYMENT', 1, null , 6), 
 	(2 , 2, 2000.00, CONCAT(CURDATE() + INTERVAL 5   day, ' 15:00:00'), 'PENDING_PAYMENT', 1, null , 7),
	(3 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 1   day, ' 11:00:00'), 'PAID'		     , 2, 1    , 6),
	(4 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 5   day, ' 11:00:00'), 'PAID'		     , 2, 2    , 6),
	(5 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 9   day, ' 11:00:00'), 'PAID'		     , 3, 3    , 6),
	(6 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 15  day, ' 15:00:00'), 'PAID'		     , 3, 4    , 7),
	(7 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 18  day, ' 15:00:00'), 'PAID'		     , 4, 5    , 7),
	(8 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 21  day, ' 15:00:00'), 'PAID'		     , 4, 6    , 7),
	(9 , 2, 2000.00, CONCAT(CURDATE() - INTERVAL 24  day, ' 15:00:00'), 'PAID'		     , 1, 7    , 7),
	(10, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 29  day, ' 09:00:00'), 'PAID'		     , 1, 8    , 8),
	(11, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 34  day, ' 09:00:00'), 'PAID'		     , 2, 9    , 8),
	(12, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 39  day, ' 09:00:00'), 'PAID'		     , 2, 10   , 8),
	(13, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 44  day, ' 09:00:00'), 'PAID'		     , 3, 11   , 8),
	(14, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 49  day, ' 09:00:00'), 'PAID'		     , 3, 12   , 8),
	(15, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 4   day, ' 13:00:00'), 'PAID'		     , 4, 13   , 9),
	(16, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 7   day, ' 13:00:00'), 'PAID'		     , 4, 14   , 9),
	(17, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 11  day, ' 07:00:00'), 'PAID'		     , 1, 15   , 10),
	(18, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 11  day, ' 11:00:00'), 'PAID'		     , 1, 16   , 10),
	(19, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 12  day, ' 15:00:00'), 'PAID'		     , 2, 17   , 10),
	(20, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 13  day, ' 07:00:00'), 'PAID'		     , 2, 18   , 10),
	(21, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 14  day, ' 11:00:00'), 'PAID'		     , 3, 19   , 10),
	(22, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 0   day, ' 17:00:00'), 'PAID'		     , 3, 20   , 11),
	(23, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 4   day, ' 17:00:00'), 'PAID'		     , 4, 21   , 11),
	(24, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 8   day, ' 17:00:00'), 'PAID'		     , 4, 22   , 11),
	(25, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 12  day, ' 17:00:00'), 'PAID'		     , 1, 23   , 11),
	(26, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 16  day, ' 17:00:00'), 'PAID'		     , 1, 24   , 11),
	(27, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 1   day, ' 07:00:00'), 'PAID'		     , 2, 25   , 12),
	(28, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 4   day, ' 07:00:00'), 'PAID'		     , 2, 26   , 12),
	(29, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 7   day, ' 07:00:00'), 'PAID'		     , 3, 27   , 12),
	(30, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 10  day, ' 07:00:00'), 'PAID'		     , 3, 28   , 12),
	(31, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 13  day, ' 07:00:00'), 'PAID'		     , 4, 29   , 12),
	(32, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 1   day, ' 11:00:00'), 'PAID'		     , 4, 30   , 13),
	(33, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 4   day, ' 11:00:00'), 'PAID'		     , 1, 31   , 13),
	(34, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 9   day, ' 11:00:00'), 'PAID'		     , 1, 32   , 13),
	(35, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 5   day, ' 15:00:00'), 'PAID'		     , 2, 33   , 14),
	(36, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 8   day, ' 15:00:00'), 'PAID'		     , 2, 34   , 14),
	(37, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 13  day, ' 09:00:00'), 'PAID'		     , 3, 35   , 15),
	(38, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 13  day, ' 13:00:00'), 'PAID'		     , 3, 36   , 15),
	(39, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 14  day, ' 17:00:00'), 'PAID'		     , 4, 37   , 15),
	(40, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 14  day, ' 09:00:00'), 'PAID'		     , 4, 38   , 15),
	(41, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 15  day, ' 13:00:00'), 'PAID'		     , 1, 39   , 15),
	(42, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 1   day, ' 08:00:00'), 'PAID'		     , 1, 40   , 16),
	(43, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 5   day, ' 08:00:00'), 'PAID'		     , 2, 41   , 16),
	(44, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 9   day, ' 08:00:00'), 'PAID'		     , 2, 42   , 16),
	(45, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 13  day, ' 12:00:00'), 'PAID'		     , 3, 43   , 17),
	(46, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 16  day, ' 12:00:00'), 'PAID'		     , 3, 44   , 17),
	(47, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 21  day, ' 16:00:00'), 'PAID'		     , 4, 45   , 18),
	(48, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 26  day, ' 16:00:00'), 'PAID'		     , 4, 46   , 18),
	(49, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 31  day, ' 16:00:00'), 'PAID'		     , 1, 47   , 18),
	(50, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 2   day, ' 16:00:00'), 'PAID'		     , 1, 48   , 18),
	(51, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 1   day, ' 09:00:00'), 'PAID'		     , 2, 49   , 19),
	(52, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 5   day, ' 07:00:00'), 'PAID'		     , 2, 50   , 20),
	(53, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 5   day, ' 11:00:00'), 'PAID'		     , 3, 51   , 20),
	(54, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 6   day, ' 15:00:00'), 'PAID'		     , 3, 52   , 20),
	(55, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 8   day, ' 13:00:00'), 'PAID'		     , 4, 53   , 21),
	(56, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 12  day, ' 13:00:00'), 'PAID'		     , 4, 54   , 21),
	(57, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 16  day, ' 17:00:00'), 'PAID'		     , 1, 55   , 22),
	(58, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 19  day, ' 17:00:00'), 'PAID'		     , 1, 56   , 22),
	(59, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 22  day, ' 17:00:00'), 'PAID'		     , 2, 57   , 22),
	(60, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 25  day, ' 17:00:00'), 'PAID'		     , 2, 58   , 22),
	(61, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 28  day, ' 17:00:00'), 'PAID'		     , 3, 59   , 22),
	(62, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 31  day, ' 07:00:00'), 'PAID'		     , 3, 60   , 23),
	(63, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 35  day, ' 07:00:00'), 'PAID'		     , 4, 61   , 23),
	(64, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 40  day, ' 07:00:00'), 'PAID'		     , 4, 62   , 23),
	(65, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 14  day, ' 11:00:00'), 'PAID'		     , 1, 63   , 24),
	(66, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 17  day, ' 11:00:00'), 'PAID'		     , 1, 64   , 24),
	(67, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 20  day, ' 08:00:00'), 'PAID'		     , 2, 65   , 25),
	(68, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 21  day, ' 16:00:00'), 'PAID'		     , 2, 66   , 25),
	(69, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 22  day, ' 12:00:00'), 'PAID'		     , 3, 67   , 25),
	(70, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 23  day, ' 08:00:00'), 'PAID'		     , 3, 68   , 25),
	(71, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 23  day, ' 16:00:00'), 'PAID'		     , 4, 69   , 25),
	(72, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 20  day, ' 15:00:00'), 'PAID'		     , 4, 70   , 26),
	(73, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 24  day, ' 15:00:00'), 'PAID'		     , 1, 71   , 26),
	(74, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 28  day, ' 15:00:00'), 'PAID'		     , 1, 72   , 26),
	(75, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 32  day, ' 15:00:00'), 'PAID'		     , 2, 73   , 26),
	(76, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 36  day, ' 15:00:00'), 'PAID'		     , 2, 74   , 26),
	(77, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 16  day, ' 08:00:00'), 'PAID'		     , 3, 75   , 27),
	(78, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 19  day, ' 08:00:00'), 'PAID'		     , 3, 76   , 27),
	(79, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 22  day, ' 08:00:00'), 'PAID'		     , 4, 77   , 27),
	(80, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 25  day, ' 08:00:00'), 'PAID'		     , 4, 78   , 27),
	(81, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 28  day, ' 08:00:00'), 'PAID'		     , 1, 79   , 27),
	(82, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 31  day, ' 08:00:00'), 'PAID'		     , 1, 80   , 27),
	(83, 2, 2000.00, CONCAT(CURDATE() - INTERVAL 34  day, ' 08:00:00'), 'PAID'		     , 2, 81   , 27),
	(84, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 1   day, ' 08:00:00'), 'PAID'		     , 2, 82   , 27),
	(85, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 4   day, ' 08:00:00'), 'PAID'		     , 3, 83   , 27),
	(86, 2, 2000.00, CONCAT(CURDATE() + INTERVAL 8   day, ' 08:00:00'), 'PAID'		     , 3, 84   , 27);
UPDATE booking b 
	JOIN tour t ON b.tour_id = t.id 
SET b.number_travelers = (FLOOR( 1 + RAND( ) * t.limit_traveler ));
UPDATE booking b 
	JOIN tour t ON b.tour_id = t.id 
SET b.price = b.number_travelers * t.price_per_traveler;

INSERT INTO `busy_schedule` (id, busy_date, type_busy_day, guide_id)
SELECT 
    ROW_NUMBER() OVER () AS id,
    DATE_ADD(b.start_date, INTERVAL n DAY) AS busy_date,
    CASE 
        WHEN t.unit = 'day(s)' THEN 'BOOKED_DAY_BY_DAYS'
        ELSE 'BOOKED_DAY_BY_HOURS'
    END AS type_busy_date,
    t.guide_id
FROM 
    booking b
JOIN
    tour t ON b.tour_id = t.id
JOIN
    ((SELECT (a.n + b.n * 10) AS n
		FROM (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a
		CROSS JOIN (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b
	)) AS numbers
WHERE 
	(t.unit = 'day(s)' AND (t.duration = 1 OR DATE_ADD(b.start_date, INTERVAL t.duration - 1 DAY) >= DATE_ADD(b.start_date, INTERVAL n DAY)))
    OR (t.unit = 'hour(s)' AND b.start_date = DATE_ADD(b.start_date, INTERVAL n DAY))
ORDER BY  guide_id ASC;

INSERT INTO `busy_schedule` (id, busy_date, type_busy_day, guide_id)
VALUES
	(202, CONCAT(CURDATE() + INTERVAL 2 day, ' 00:00:00'), 'DATE_SELECTED_BY_GUIDE', 4),
	(203, CONCAT(CURDATE() + INTERVAL 3 day, ' 00:00:00'), 'DATE_SELECTED_BY_GUIDE', 4),
	(204, CONCAT(CURDATE() + INTERVAL 4 day, ' 00:00:00'), 'DATE_SELECTED_BY_GUIDE', 4);

INSERT INTO `invoice` (id, create_at, price_total, traveler_id, email, full_name, phone, status)
SELECT 
	b.invoice_id as id,
	b.start_date - INTERVAL (FLOOR( 1 + RAND( ) * 10 ))  day as create_at,
    b.price,
    u.id as traveler_id,
    u.email,
    u.full_name,
    u.phone,
    'PAID' as `status`
FROM local_guide.booking b 
JOIN cart c on b.cart_id = c.id
JOIN `user` u on u.id = c.traveler_id
WHERE `status` = 'PAID';
		
INSERT INTO `traveler_request` (id, destination, duration, max_price_per_person, message, `status`, transportation, unit, guide_id, tour_id, traveler_id, number_of_travelers, phone)
VALUES 
	-- ('1', 'Ho Chi Minh' , '5', '500' , 'Looking forward to exploring Ho Chi Minh!' , 'PENDING' , 'car, bus'        , 'day(s)', 4, NULL, '10', 1),
	('2', 'Ha Noi'		, '5', '80'  , 'Looking forward to exploring Ha Noi!'	   , 'ACCEPTED', 'car, bus'		   , 'day(s)', 5, NULL, '10', 2, '0373228584'),
	('3', 'Hue - Danang', '2', '1000', 'Looking forward to exploring Hue - Danang!', 'DENIED'  , 'Bicycle, Boat'   , 'day(s)', 5, NULL, '10', 3, '0373228584'),
	('4', 'Hoi An'		, '2', '398' , 'Looking forward to exploring Hoi An!'      , 'DONE'    , 'Bicycle, Walking', 'day(s)', 5, 31,   '10', 4, '0373228584'),
    ('5', 'Phu Quoc'	, '2', '398' , 'Looking forward to exploring Phu Quoc!'    , 'CANCELED', 'Bicycle, Walking', 'day(s)', 5, NULL, '10', 5, '0373228584')
    -- ( '6', 'Nha Trang'   , '2', '398' , 'Looking forward to exploring Nha Trang!'   , 'DRAFT'   , 'Bicycle, Walking', 'day(s)', 4, NULL, '10', 6)
    ;

-- INSERT INTO `notification` (id, associate_id, is_read, message, notification_date, notification_type, receiver_id, sender_id)
-- VALUES 
-- 	(1, 1, 0, 'hihihihihi 1', CONCAT(CURDATE() - INTERVAL 1 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(2, 1, 0, 'hihihihihi 2', CONCAT(CURDATE() - INTERVAL 2 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(3, 1, 0, 'hihihihihi 3', CONCAT(CURDATE() - INTERVAL 3 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(4, 1, 0, 'hihihihihi 4', CONCAT(CURDATE() - INTERVAL 4 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
--     (5, 1, 0, 'hihihihihi 5', CONCAT(CURDATE() - INTERVAL 5 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(6, 1, 0, 'hihihihihi 6', CONCAT(CURDATE() - INTERVAL 6 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(7, 1, 0, 'hihihihihi 7', CONCAT(CURDATE() - INTERVAL 7 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(8, 1, 0, 'hihihihihi 8', CONCAT(CURDATE() - INTERVAL 8 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
--     (9, 1, 0, 'hihihihihi 9', CONCAT(CURDATE() - INTERVAL 9 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(10, 1, 0, 'hihihihihi 10', CONCAT(CURDATE() - INTERVAL 10 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(11, 1, 0, 'hihihihihi 11', CONCAT(CURDATE() - INTERVAL 11 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(12, 1, 0, 'hihihihihi 12', CONCAT(CURDATE() - INTERVAL 12 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
--     (13, 1, 0, 'hihihihihi 13', CONCAT(CURDATE() - INTERVAL 13 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(14, 1, 0, 'hihihihihi 14', CONCAT(CURDATE() - INTERVAL 14 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(15, 1, 0, 'hihihihihi 15', CONCAT(CURDATE() - INTERVAL 15 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1),
-- 	(16, 1, 0, 'hihihihihi 16', CONCAT(CURDATE() - INTERVAL 16 day, ' 00:00:00'), 'TOUR_REVIEW', 2, 1);

-- Insert review for tour
INSERT INTO `review` (id, `comment`, created_at, rating, guide_id, parent_id, tour_id, traveler_id )
SELECT 
	ROW_NUMBER() OVER () AS id,
    SUBSTRING_INDEX(s.comment, '.', 1) as comment,
    created_at,
    SUBSTRING_INDEX(SUBSTRING_INDEX(s.comment, ' ', -1), '.', 1) AS rating,
    NULL AS guide_id,
    NULL AS parent_id,
    s.tour_id,
    s.traveler_id
FROM 
    (
        SELECT 
            b.id,
            (
                SELECT value
                FROM (
                    SELECT 'The tour guide was incredibly knowledgeable and made the whole experience truly immersive. 5' AS value
                    UNION ALL SELECT 'The itinerary was well-planned, allowing us to see all the highlights without feeling rushed. 5'
                    UNION ALL SELECT 'The accommodations provided during the tour were top-notch, adding to the overall comfort of the trip. 5'
                    UNION ALL SELECT 'I appreciated the small group size, as it allowed for a more personalized and intimate experience. 4'
                    UNION ALL SELECT 'The tour company\'s attention to detail really stood out, from transportation to dining options. 5'
                    UNION ALL SELECT 'The tour offered unique insights into the local culture and history, making it both educational and enjoyable. 5'
                    UNION ALL SELECT 'I was impressed by the professionalism of the tour guides and their ability to handle unexpected situations smoothly. 5'
                    UNION ALL SELECT 'The activities included in the tour were diverse and catered to different interests, ensuring there was something for everyone. 4'
                    UNION ALL SELECT 'The pace of the tour was just right, balancing sightseeing with leisure time perfectly. 5'
                    UNION ALL SELECT 'Overall, the tour exceeded my expectations and left me with unforgettable memories. 4'
                    UNION ALL SELECT 'The tour guide seemed disinterested and lacked knowledge about the places we visited. 2'
                    UNION ALL SELECT 'The itinerary felt rushed, leaving little time to fully appreciate the attractions. 3'
                    UNION ALL SELECT 'The accommodations provided were subpar and didn\'t meet the advertised standards. 2'
                    UNION ALL SELECT 'The group size was too large, making it difficult to hear the guide and causing delays. 2'
                    UNION ALL SELECT 'The transportation arrangements were poorly organized, leading to delays and discomfort. 1'
                    UNION ALL SELECT 'The tour lacked depth and only scratched the surface of the destinations we visited. 1'
                    UNION ALL SELECT 'Many of the included activities felt like tourist traps and didn\'t offer authentic experiences. 2'
                    UNION ALL SELECT 'The tour company failed to communicate important information, leading to confusion among participants. 2'
                    UNION ALL SELECT 'The meals provided during the tour were uninspiring and of low quality. 3'
                    UNION ALL SELECT 'Overall, I felt disappointed by the lackluster experience and wouldn\'t recommend this tour to others. 3'
                ) AS strings
                ORDER BY RAND()
                LIMIT 1
            ) AS comment,
            DATE_ADD(b.start_date, INTERVAL FLOOR(t.duration + 1 + RAND() * 20) DAY) AS created_at,
            c.traveler_id, 
            b.tour_id
        FROM booking b 
            JOIN cart c ON b.cart_id = c.id
            JOIN tour t ON b.tour_id = t.id
        WHERE b.status = 'PAID' 
    ) AS s
WHERE s.created_at < CURDATE();
UPDATE tour t
JOIN 
	(
		SELECT t.id AS tour_id, ROUND(avg(r.rating), 1) as overall_rating
		FROM local_guide.review r
			JOIN tour t ON r.tour_id = t.id
		GROUP BY t.id
	) AS overall_ratings ON t.id = overall_ratings.tour_id
SET t.overall_rating = overall_ratings.overall_rating;

-- Insert review for guide
INSERT INTO `review` (id, `comment`, created_at, rating, guide_id, parent_id, tour_id, traveler_id )
SELECT 
	ROW_NUMBER() OVER () + 84 AS id,
    SUBSTRING_INDEX(s.comment, '.', 1) as comment,
    created_at,
    SUBSTRING_INDEX(SUBSTRING_INDEX(s.comment, ' ', -1), '.', 1) AS rating,
    s.guide_id,
    NULL AS parent_id,
    NULL AS tour_id,
    s.traveler_id
FROM 
    (
        SELECT 
            b.id,
            (
                SELECT value
                FROM (
                    SELECT 'The tour guide was incredibly knowledgeable and made the whole experience truly immersive. 5' AS value
                    UNION ALL SELECT 'The itinerary was well-planned, allowing us to see all the highlights without feeling rushed. 5'
                    UNION ALL SELECT 'The accommodations provided during the tour were top-notch, adding to the overall comfort of the trip. 5'
                    UNION ALL SELECT 'I appreciated the small group size, as it allowed for a more personalized and intimate experience. 4'
                    UNION ALL SELECT 'The tour company\'s attention to detail really stood out, from transportation to dining options. 5'
                    UNION ALL SELECT 'The tour offered unique insights into the local culture and history, making it both educational and enjoyable. 5'
                    UNION ALL SELECT 'I was impressed by the professionalism of the tour guides and their ability to handle unexpected situations smoothly. 5'
                    UNION ALL SELECT 'The activities included in the tour were diverse and catered to different interests, ensuring there was something for everyone. 4'
                    UNION ALL SELECT 'The pace of the tour was just right, balancing sightseeing with leisure time perfectly. 5'
                    UNION ALL SELECT 'Overall, the tour exceeded my expectations and left me with unforgettable memories. 4'
                    UNION ALL SELECT 'The tour guide seemed disinterested and lacked knowledge about the places we visited. 2'
                    UNION ALL SELECT 'The itinerary felt rushed, leaving little time to fully appreciate the attractions. 3'
                    UNION ALL SELECT 'The accommodations provided were subpar and didn\'t meet the advertised standards. 2'
                    UNION ALL SELECT 'The group size was too large, making it difficult to hear the guide and causing delays. 2'
                    UNION ALL SELECT 'The transportation arrangements were poorly organized, leading to delays and discomfort. 1'
                    UNION ALL SELECT 'The tour lacked depth and only scratched the surface of the destinations we visited. 1'
                    UNION ALL SELECT 'Many of the included activities felt like tourist traps and didn\'t offer authentic experiences. 2'
                    UNION ALL SELECT 'The tour company failed to communicate important information, leading to confusion among participants. 2'
                    UNION ALL SELECT 'The meals provided during the tour were uninspiring and of low quality. 3'
                    UNION ALL SELECT 'Overall, I felt disappointed by the lackluster experience and wouldn\'t recommend this tour to others. 3'
                ) AS strings
                ORDER BY RAND()
                LIMIT 1
            ) AS comment,
            DATE_ADD(b.start_date, INTERVAL FLOOR(t.duration + 1 + RAND() * 20) DAY) AS created_at,
            c.traveler_id, 
            b.tour_id,
            t.guide_id
        FROM booking b 
            JOIN cart c ON b.cart_id = c.id
            JOIN tour t ON b.tour_id = t.id
        WHERE b.status = 'PAID' 
    ) AS s
WHERE s.created_at < CURDATE();
UPDATE user u
JOIN user_role ur on u.id = ur.user_id
JOIN 
	(
		SELECT u.id AS guide_id, ROUND(avg(r.rating), 1) as overall_rating
		FROM review r
			JOIN user u ON r.guide_id = u.id
		GROUP BY u.id
	) AS overall_ratings ON u.id = overall_ratings.guide_id
SET u.overall_rating = overall_ratings.overall_rating
WHERE ur.role_id = 2;

SET FOREIGN_KEY_CHECKS=1; 
-- SET SESSION SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

