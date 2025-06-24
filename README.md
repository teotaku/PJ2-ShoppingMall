# BE
#  ショッピングモール バックエンドプロジェクト

ユーザーが商品を検索してカートに追加し、注文まで行えるECサイトのバックエンドを実装しました。  
管理者は商品を登録・修正・在庫管理できる管理画面機能も備えています。

---

##  プロジェクト概要

###  ユーザー機能
- ユーザー登録 / ログイン（JWT 認証）
- 商品一覧の取得（並び替え・ページネーション対応）
- カートに追加・数量変更・削除
- 注文作成、およびマイページで注文履歴・情報編集

###  管理者機能
- 商品の登録 / 編集 / 削除
- 商品画像のアップロード（AWS S3連携）
- 在庫数の管理
- 注文履歴の一覧確認

---

##  使用技術スタック

| 分野 | 技術 |
|------|------|
| バックエンド | Java 17, Spring Boot, JPA, Spring Security (JWT) |
| データベース | MySQL（RDS）, H2（テスト用） |
| インフラ | AWS EC2, RDS, S3 |
| CI/CD | GitHub Actions, Docker, ECR |
| その他 | Swagger（OpenAPI 3）, Validation, Docker Compose, GitHub Secrets |

---

##  インフラ構成図

ユーザー ↔ EC2 (Spring Boot) ↔ RDS (MySQL)
↘︎ 画像アップロード → S3


---

##  環境分離構成

本番環境のインフラ（EC2, RDS, S3）は重く、コストも高いため、開発・テスト環境は軽量なローカルDockerベースで構成し、  
画像アップロードのみ実際のS3と連携する構成にしました。

| プロファイル | 用途         | 特徴 |
|--------------|--------------|------|
| dev          | 開発環境     | Docker Compose + S3接続（MySQL/Redisはローカル） |
| prod         | 本番環境     | EC2 + RDS + S3 + GitHub Actions（自動デプロイ） |
| test         | テスト環境   | H2 + Mock（S3などの外部依存を排除） |

```bash
### 開発環境実行
./gradlew bootRun -Dspring.profiles.active=dev

### テスト実行
./gradlew test -Dspring.profiles.active=test

### CI/CD 自動デプロイ構成
GitHub Actionsを活用し、コードのPush → Dockerビルド → ECRプッシュ → EC2デプロイまで全自動化しました。

フロー概要
GitHub Push トリガー

Docker イメージビルド

Amazon ECR へプッシュ

EC2 サーバーがイメージを pull → 実行

application-prod.yml は GitHub Secrets を用いて echo により自動生成

→ .env ファイル不要で本番環境を安全に起動可能


### なぜ環境を分離したのか（導入背景）
### 導入背景
当初は開発やテストをすべて本番環境（EC2, RDS）上で行っていたため、

処理速度の低下

AWS費用の増大

設定ミスによる本番環境の破損リスク

といった問題が発生していました。

- 使用理由
本番環境はリソースが重いため、開発・テストではローカルで素早く動かせる構成が必要

本番と同じ構成を保ちつつ、画像のみS3にアップロード

外部依存を排除して高速にテストできる Mock 構成のtest profileを導入

- 実装内容
application.yml: 共通設定

application-dev.yml: ローカル開発用（Docker Compose）

application-prod.yml: 本番環境（Secretsベースで生成）

application-test.yml: 単体テスト専用（H2 + Mock）

✅ 導入後の効果
本番リソース未使用により AWSコスト大幅削減

デプロイ/テスト速度大幅向上

プロファイル別設定で安定運用

CI/CD + Dockerで本番と開発の実行環境を統一

✅ テスト構成
Spring Boot 標準の @SpringBootTest, @WebMvcTest, @DataJpaTest 使用

Controller/Service層にて MockMvc, @MockBean, @WithMockUser 活用

S3Uploaderなどの外部サービスは @Profile("test") でMock化し、安全に検証可能



✅ エラー対応記録

---1. ECRログインの重複問題-------------------------------------------

###  発生した問題  
GitHub ActionsでCI/CDパイプラインを構築する際に、  
`aws ecr get-login-password`を使って **Dockerログインを2回実行**した結果、  
2回目のログイン時にECRの認証エラーが発生しました。  

GitHub Actions側でログインした後、EC2上でも再度ログインを試みたことが原因で、  
コンテナ実行時に認証の衝突が起こりました。

---

###  原因の分析と対応の試み  
- GitHub Actions内で `docker login` → `docker push` までは完了済み  
- EC2に接続後、再度 `docker login` を行ったことで認証トークンが競合  
- **EC2ではECRログインを省略し、`docker pull`と`docker run`のみ実行**する構成に変更

---

### ✅ 最終的な結果  
- ECRの二重ログインによる認証エラーが解消  
- EC2上でDockerコンテナの実行が正常に動作  
- CI/CD全体のフローが安定し、自動デプロイが成功するようになった

---

###  トラブルシューティングまとめ  

| 項目 | 内容 |
|------|------|
|  問題 | ECRの認証競合（Dockerログインの重複） |
|  原因 | GitHub ActionsとEC2で同じ認証を繰り返したため |
|  対応 | EC2側ではログイン省略、pullとrunのみ実行 |
|  結果 | 認証エラー解決、CI/CD安定化

-------------------------------------------------------------



🗓️ 開発期間
2025年5月 ～ 2025年6月（約4週間）

👤 開発者
名前: 이동익 イ・ドンイク（Lee Dong Ik）

Email: dongki9467@naver.com

GitHub: https://github.com/teotaku
