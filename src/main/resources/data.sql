-- Member

insert
  into member (o_auth_id, name, email, gender, profile_image, social, is_first_time, role, created_at, updated_at)
  values (
  1234567890,
  '홍길동',
  'hong@gmail.com',
  'male',
  'https://cdn-std-web-228-253.cdn-nhncommerce.com/encom121111_godomall_com/data/goods/23/10/40/1000000024/1000000024_detail_011.jpg',
  'kakao',
  true,
  'ROLE_USER',
  NOW(),
  NOW());

insert
  into member (o_auth_id, name, email, gender, profile_image, social, is_first_time, role, created_at, updated_at)
  values (
  1234567891,
  '유하나',
  'oneYoo@naver.com',
  'female',
  'https://cdn-std-web-228-253.cdn-nhncommerce.com/encom121111_godomall_com/data/goods/23/10/40/1000000024/1000000024_detail_011.jpg',
  'kakao',
  true,
  'ROLE_USER',
  NOW(),
  NOW());

insert
  into member (o_auth_id, name, email, gender, profile_image, social, is_first_time, role, created_at, updated_at)
  values (
  1234567892,
  '김대한',
  'daehan@naver.com',
  'male',
  'https://cdn-std-web-228-253.cdn-nhncommerce.com/encom121111_godomall_com/data/goods/23/10/40/1000000024/1000000024_detail_011.jpg',
  'kakao',
  true,
  'ROLE_USER',
  NOW(),
  NOW());
-- Category

insert
  into category (name)
  values ('연애');
insert
  into category (name)
  values ('결혼');

-- Question

-- 에피소드 1 : 소개팅 및 만남

-- 1-1
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '친구가 연락와서 소개팅할거냐고 물어봤다. 키,외모,성격 모두 내 타입이다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/1-1/story1_1.png',
       '10살 차이나는 이성의 소개팅이 들어왔다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/1-1/question1_1.png',
       '“근데 나이가...”',
       1
       );

-- 1-2
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '소개팅으로 만난 그 사람이 마음에 들어서 몇번의 만남 후 연인이 되었다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/1-2/story1_2.png',
       '내 애인의 직업은',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/1-2/question1_2.png',
       '',
       1
       );

-- 1-3
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '커플이 된 우리. 이제 하나하나 맞춰나가기로 했다. 서로 연애 스타일에 대해 물어보면서 맞춰나가고 있는데...',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/1-3/story1_3.png',
       '데이트는 어떻게 하는게 좋을까?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/1-3/question1_3.png',
       '',
       1
       );

-- 에피소드2 : 전애인과의 관계

-- 2-1
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '카페에서 내 애인과 대화 도중 우연히 X와의 이야기를 듣게 되었다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/2-1/story2_1.png',
       '내 애인의 X와의 관계',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/2-1/question2_1.png',
       '“그 사람이랑 헤어지고 나서..”',
       1
       );

-- 2-2
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '내 애인의 이전 연애에 대해 얘기를 듣는 도중 충격적인 사실을 알게 되었다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/2-2/story2_2.png',
       '전애인과의 동거한 사실을 알았다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/2-2/question2_2.png',
       '',
       1
       );

-- 에피소드3 : 연인 과정

-- 3-1
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '서로 맞춰나가는 과정에서 기념일에 대한 이야기가 나왔다',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-1/story3_1.png',
       '기념일 어떻게 챙길까?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-1/question3_1.png',
       '',
       1
       );

-- 3-2
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '데이트가 없는 날, 뒹굴뒹굴 집에서 넷플릭스를 보고 있었다. 그런데, 애인에게 갑작스럽게 연락이 왔다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-2/story3_2.png',
       '연락도 없이 집앞으로 찾아온 애인',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-2/question3_2.png',
       '',
       1
       );

-- 3-3
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '친구를 정말 좋아하는 내 애인. 데이트마다 친구 커플과 더블 데이트를 하자고 한다',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-3/story3_3.png',
       '내 애인의 친구 커플과의더블 데이트',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-3/question3_3.png',
       '',
       1
       );

-- 3-4
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '잠들기 전 통화하면 애인이 매번 본인의 힘든 얘기만 계속 한다. 오늘도 1시간 넘게 힘든 얘기만 하고 있다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-4/story3_4.png',
       '힘든 얘기만 계속하는 내 애인',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-4/question3_4.png',
       '',
       1
       );

-- 3-5
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '어제 밤에 좋은 꿈을 꾸고 산 로또! 번호를 맞춰보니 1억에 당첨되었다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-5/story3_5.png',
       '1억 로또 당첨금, 애인에게 말해야하나?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-5/question3_5.png',
       '',
       1
       );

-- 3-6
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '사귀는 날이 점점 지나갈수록 점점 싸우는 횟수가 잦아들고 있다. 오늘도 별일 아닌 일로 싸우게 됐는데...',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-6/story3_6.png',
       '싸운 후 불난 집에 부채질하는 애인의 행동',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/3-6/question3_6.png',
       '“또 이러네...”',
       1
       );

-- 에피소드4 : 스킨십 관련

-- 4-1
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '오랜만에 심야 영화를 보러 간 우리. 영화를 보다가 눈이 마주쳤다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-1/story4_1.png',
       '눈이 마주친 우리는',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-1/question4_1.png',
       '',
       1
       );

-- 4-2
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '영화관에서 돌아온 우리. 맥주도 한잔하고 분위기도 무르익었는데 실내 조명이 환하게 켜져 있다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-2/story4_2.png',
       '조명, 어떤걸 선호해?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-2/question4_2.png',
       '',
       1
       );

-- 4-3
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '절정에 다다른 우리, 내가 선호하는 포지션은...',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-3/story4_3.png',
       '침대 위에서 나는',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-3/question4_3.png',
       '',
       1
       );

-- 4-4
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '만족스럽지 않았던 우리의 잠자리, 사실 내가 원하는 뜨밤 시간이 있었는데...',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-4/story4_4.png',
       '내가 선호하는 뜨밤 시간',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/4-4/question4_4.png',
       '',
       1
       );

-- 에피소드5 : 이성 문제

-- 5-1
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '부쩍 약속이 많은 내 애인. 알고보니 내 애인이 이성 친구가 많다는 것을 알게 되었다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/5-1/story5_1.png',
       '내 애인의 이성 친구',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/5-1/question5_1.png',
       '',
       1
       );

-- 5-2
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '갑작스럽게 애인에게 온 연락. 애인의 이성 친구가 고민이 있어서 단 둘이 술마시러 가야할 것 같다고 연락이 왔다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/5-2/story5_2.png',
       '내 애인과 이성친구와의 둘만의 술자리',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/5-2/question5_2.png',
       '',
       1
       );

-- 5-3
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '결국 들켜버린 내 애인의 이성 문제. 자꾸 바람이 아니라고 하는 내 애인',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/5-3/story5_3.png',
       '바람의 기준은?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/5-3/question5_3.png',
       '',
       1
       );

-- 에피소드6 : 이별 문제

-- 6-1
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '서로에게 소홀해지고 자주 싸우게 되면서 결국 헤어지게 되었다.',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/6-1/story6_1.png',
       '내가 절대 용서 못하는 이별은?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/6-1/question6_1.png',
       '',
       1
       );

-- 6-2
insert
  into question (situation, situation_image, title, title_image, sub_title, category_id)
  values (
       '헤어지고 난 후, 이별에 슬퍼하는 나. 친구한테 소개팅 연락이 왔다 .',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/6-2/story6_2.png',
       '이별 후 일주일 만에 소개팅?',
       'https://gachee-game-s3.s3.ap-northeast-2.amazonaws.com/assets/imgs/Question/6-2/question6_2.png',
       '“괜찮은 사람인데 소개팅 받을래?”',
       1
       );

-- Answer

-- 에피소드 1 : 소개팅 및 만남

-- 1-1
insert
  into answer (answer_content, question_id)
  values (
       '위로 10살을 받는다',
       1
       );

insert
  into answer (answer_content, question_id)
  values (
       '아래로 10살을 받는다',
       1
       );

-- 1-2
insert
  into answer (answer_content, question_id)
  values (
       '연봉 7천 대기업 대리',
       2
       );

insert
  into answer (answer_content, question_id)
  values (
       '연봉 추정불가 스타트업 대표',
       2
       );

-- 1-3
insert
  into answer (answer_content, question_id)
  values (
       '일주일에 3번',
       3
       );

insert
  into answer (answer_content, question_id)
  values (
       '한 달에 2번',
       3
       );

-- 에피소드2 : 전애인과의 관계

-- 2-1

insert
  into answer (answer_content, question_id)
  values (
       '친구로 지내도 괜찮다',
       4
       );

insert
  into answer (answer_content, question_id)
  values (
       '헤어지는 순간 남보다 못하다',
       4
       );

-- 2-2

insert
  into answer (answer_content, question_id)
  values (
       '과거는 과거일뿐. 이해한다.',
       5
       );

insert
  into answer (answer_content, question_id)
  values (
       '나한테는 있을 수 없는 일. 헤어진다.',
       5
       );

-- 에피소드3 : 연인 과정

-- 3-1

insert
  into answer (answer_content, question_id)
  values (
       '100일 단위에 00데이 다 챙기자! ex) 발렌타인데이, 화이트데이 등',
       6
       );

insert
  into answer (answer_content, question_id)
  values (
       'only 1년 단위로 챙기자',
       6
       );

-- 3-2

insert
  into answer (answer_content, question_id)
  values (
       '반갑게 맞이한다.',
       7
       );

insert
  into answer (answer_content, question_id)
  values (
       '집에 없는 척 한다.',
       7
       );

-- 3-3

insert
  into answer (answer_content, question_id)
  values (
       '사람 많으면 더 재밌지! 오히려 좋아!',
       8
       );

insert
  into answer (answer_content, question_id)
  values (
       '우리 둘만의 시간이 좋은데.. ',
       8
       );

-- 3-4

insert
  into answer (answer_content, question_id)
  values (
       '공감하면서 계속 들어준다.',
       9
       );

insert
  into answer (answer_content, question_id)
  values (
       '타이밍봐서 졸리다고 끊는다.',
       9
       );

-- 3-5

insert
  into answer (answer_content, question_id)
  values (
       '애인한테 바로 알린다',
       10
       );

insert
  into answer (answer_content, question_id)
  values (
       '애인에게 알리지 않는다.',
       10
       );

-- 3-6

insert
  into answer (answer_content, question_id)
  values (
       '싸운 내용을 친구들에게 다 말하는 애인',
       11
       );

insert
  into answer (answer_content, question_id)
  values (
       '아무렇지 않게 SNS에 술마시고 노는 걸 올리는 애인',
       11
       );

-- 에피소드4 : 스킨십 관련

-- 4-1

insert
  into answer (answer_content, question_id)
  values (
       '영화고 뭐고 바로 나간다.',
       12
       );

insert
  into answer (answer_content, question_id)
  values (
       '참고 영화에 집중한다.',
       12
       );

-- 4-2

insert
  into answer (answer_content, question_id)
  values (
       '그대로 키고 한다.',
       13
       );

insert
  into answer (answer_content, question_id)
  values (
       '끄고 한다.',
       13
       );

-- 4-3

insert
  into answer (answer_content, question_id)
  values (
       '리드하는 편',
       14
       );

insert
  into answer (answer_content, question_id)
  values (
       '받는 편',
       14
       );

-- 4-4

insert
  into answer (answer_content, question_id)
  values (
       '1분',
       15
       );

insert
  into answer (answer_content, question_id)
  values (
       '1시간 ',
       15
       );

-- 에피소드5 : 이성 문제

-- 5-1

insert
  into answer (answer_content, question_id)
  values (
       '친구는 친구일 뿐. 상관없다.',
       16
       );

insert
  into answer (answer_content, question_id)
  values (
       '남녀 사이에 친구? 이해 못한다.',
       16
       );

-- 5-2

insert
  into answer (answer_content, question_id)
  values (
       '고민 상담 잘해주라며 허락해준다.',
       17
       );

insert
  into answer (answer_content, question_id)
  values (
       '단 둘이 술을? 절대 안 보내준다.',
       17
       );

-- 5-3

insert
  into answer (answer_content, question_id)
  values (
       '바람의 기준은 스킨십이지',
       18
       );

insert
  into answer (answer_content, question_id)
  values (
       '마음이 흔들렸으면 바람이지',
       18
       );

-- 에피소드6 : 이별 문제

-- 6-1

insert
  into answer (answer_content, question_id)
  values (
       '환승 이별',
       19
       );

insert
  into answer (answer_content, question_id)
  values (
       '잠수 이별',
       19
       );

-- 6-2

insert
  into answer (answer_content, question_id)
  values (
       '사랑은 사랑으로 잊어야지. 바로 받는다.',
       20
       );

insert
  into answer (answer_content, question_id)
  values (
       '바로 받는건 예의가 없지. 안 받는다.',
       20
       );

-- MemberAnswer

insert
  into member_answer (answer_id, member_id, created_at, updated_at, category_id)
  values (1, 1, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, created_at, updated_at, category_id)
  values (3, 1, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, matched_member_id, created_at, updated_at, category_id)
  values (2, 1, 2, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, matched_member_id, created_at, updated_at, category_id)
  values (3, 1, 2, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, created_at, updated_at, category_id)
  values (1, 2, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, created_at, updated_at, category_id)
  values (3, 2, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, matched_member_id, created_at, updated_at, category_id)
  values (1, 2, 1, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, matched_member_id, created_at, updated_at, category_id)
  values (3, 2, 1, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, matched_member_id, created_at, updated_at, category_id)
  values (2, 3, 1, NOW(), NOW(), 1);

insert
  into member_answer (answer_id, member_id, matched_member_id, created_at, updated_at, category_id)
  values (4, 3, 1, NOW(), NOW(), 1);
