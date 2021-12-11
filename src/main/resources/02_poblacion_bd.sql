INSERT INTO `USUARIO` (`ID`, `USUARIO`, `PASSWORD`, `FECHA_ALTA`, `ACTIVO`, `ROL`, `IDIOMA`) VALUES
(1, 'ADMIN', '123456', '2021-12-11', 1, 1, 'ES'),
(2, 'LEYRE1', '123456', '2021-12-11', 0, 3, 'ES'),
(3, 'SEFIP', '123456', '2021-12-11', 1, 3, 'EN'),
(4, 'LOGOMED', '123456', '2021-12-11', 1, 3, 'ES'),
(5, 'AIDA1', '123456', '2021-12-11', 1, 2, 'ES'),
(6, 'MARY2', '123456', '2021-12-11', 1, 2, 'EN'),
(7, 'MANOLI3', '123456', '2021-12-11', 0, 2, 'ES');


INSERT INTO `CLIENTE` (`USUARIO`, `NOMBRE`, `APELLIDOS`, `FECHA_NACIMIENTO`, `DOMICILIO`, `LOCALIDAD`, `CP`) VALUES
(5, 'Aida', 'Rodríguez Jiménez', '1988-06-03', 'Calle San Fernando, 23', 'Bormujos', 41930),
(6, 'Mary', 'Townsend', '1996-10-24', 'Carrer de la Riviera, 48', 'Barcelona', 28087),
(7, 'Manoli', 'García', '1986-04-17', 'Calle del palo, 2', 'Tomares', 41017);


INSERT INTO `PACIENTE` (`ID`, `CLIENTE`, `NOMBRE`, `FECHA_NACIMIENTO`, `OBSERVACIONES`) VALUES
(1, 5, 'Maggie', '2021-09-24', 'Cólicos del lactante'),
(2, 6, 'Gael', '2018-01-13', 'Piel atópica'),
(3, 6, 'Gael', '2018-01-13', 'Atopic skin'),
(4, 6, 'Leo', '2013-08-12', '');


INSERT INTO `PROVEEDOR` (`USUARIO`, `RAZON_SOCIAL`, `CIF`, `DIRECCION`, `LOCALIDAD`, `CP`, `LOGO`, `RESPONSABLE`) VALUES
(2, 'Leyre Martí', 'A1234567', 'Calle Ancha, 24', 'Madrid', 25896, 0xffd8ffe000104a46494600010200000100010000ffdb004300080606070605080707070909080a0c140d0c0b0b0c1912130f141d1a1f1e1d1a1c1c20242e2720222c231c1c2837292c30313434341f27393d38323c2e333432ffdb0043010909090c0b0c180d0d1832211c213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232ffc000110800c800c803012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00f7fa28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2803197c43066f0c90ba2d9b849581cf56db91ea335b35c24dfea3c4dff005dd7ff00469aea6cefa0b6d22c1ae650a5e04e4e4f60327d0648e4f1c8af27058d94e4e355f4bffe4cd7f90172ea716b6935c15dc2242e40ee00c9aa11eb692cda7a2c1262f15883fddc549ad5cc516997313be1e4824da31d7e5fd39207e22aae8935a8d3b4d8a429f683193164738e7383f856f56b4beb1eca3249593fc569f3406dd37782e533f300091ec7ff00d46ab8d42d4de0b5f33f7cd9da0a901b1d7071838acdd3f534bad6b513b5c2c623897e43938df9cfa724d6f3c4d38ca31beeedf85c0dcaaba7dfc7a95a2dcc492246c485de00271c67827bd361d56c6e3021b859339c6d04f419fe59fc8fa543a7496965a35b08a40f12aed0d1a93bdb9dd80324f209a3db2735cb25cb677fc2dfd7a01a545436d750de40b3dbc8248dba30a9ab78c9495d6c0145145300a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a28038494130f8940192675c7fdfd3535db3a4d7568fd6d74b588e0e4139424febfa55dd0618e5d7b586923562971952c3383b9b91514b68d7de2dd52d84823125b80cc5777184e9c8af96f6327494a3bca4e3f8cdfe76fb8424b2b4f233c8c598e8658e7d4f24d3ec3fd6f86bfeb9cdffa0d47343f66d46fadf76e116905036319c002a4b1e25f0dff00d739bff41a237f6bef6f75ff00a5c46430437f6977a1c57c7122cd2aa0dd92130a3191f8fe157fc3dff21ad73febb8fe6f59b6f712dedee8d793b969659e5cfa281b40007615a1a148b16b1ae339c0370abf896603f535be15c7db41c6f6e6ebbdbd9f51199e1bb4b898584d183e54571299181e80a2e07e3cfe756f4a82fe4d374496cdb1147248271bb1952fe9df806ad7837fe408ff00f5d9bf90acfb2bc9e1d2b43b489f647732bac8470c407e80f6ebf5ace8d3a74a9d2936fde8df4ef7825f90cbfe0bff00903cdff5f07ff415ae8eb97f0add4365e1cbcbbb9904704123cb2c8dd155514927e805743657b6ba959457965711dc5b4cbba39636dcac3d8d7b196ffba53f4027a2a86a3ade9ba4c1713dfde476f15b223cccfd115d8aa93f5604542fe25d16396089b54b50f3cab0c40480ee76675551ee5a3703dd48aee03568acfd1f5cd33c4166d79a4de47776eae6332479c06001c7e447e756aeaea0b2b49aeee6411c1046d248e7a2aa8c927e801a009a8ac05f16e8f7d7c9a769bab59c97ec2297cb20b662628d918c75461839ea41e7a56fd0014564ea7e26d17469a58b51d460b6921816e1d5cf2b197d81be9b881f5356f4fbb5bf80dd453c535acdb64b768d48fddb22919c9e49249cf1c1031c64805ba28a2800a28a2800a28a2800a28a28039af0effc8735bffaefff00b3354eb12daf8c2e2e18e124b2f3198f4182a0fe82b5a1b0b3b67df05ac113e31b92300fe952490432b2349123b21ca96504a9f6af3e9e0e51a518b7aa9737e2ff00cc0e6b500f1dedc497002bbe90cae4742f9c103f1c7e74db2ff5be1bff00ae737fe835d34b6f0ce4196249080546e50700f51f8d4634fb2568d85a400c7f7088c7cbce78f4e79ac65974bda3945e9a7fe949fe807176171146ba26f9154452cace49fba323afe55aba64606b5abc1230477ba89d41ead8667fe42b7d74fb345555b585555fcc501070debf5a79b4b7699e530466475d8cfb46597d09f4a5432e9d3e5bc93b35ff00a4f28187e0de3447ff00aecdfc856342eb1da786ddd82aacd2124f6f9c57691d95ac313c715b4291b8c3aaa001bea3bd3574fb240816d20508dbd71181b5bd47bd3965f374e9c3997baadf8c5fe80713ff0034bbc50a7822d2eb20f6fdcd719adeb5359db6bf73e1cd5a5b2d35910e9de5ce12046f36e6495c0c1e5ded64450301bcd008c118f6a6b0b368ae226b581a3b9044e8d182250720861dc1c9ebeb514ba36973dbbdbcda6d9c903c690b46f029564424a290460aa92481d064e2bbb0945d0a31a4ddec0709e30d4ec2ea4d5521d4ece172ba68f3262a563f2b517491d94919546e1ba0ed9e6b32fed2dad6eaf750b678e6b38f59853cc2048ab6f937324aa4700a35d4e43738118e32335e9773a0e8f78f23dd693613b489e5bb4b6c8c5937efda72391bfe6c7af3d6a66d32c1ed8db3595b340430311894a90c08618c6390483eb9ae803ccac35a8f4df851e1c9acafed27f2de292f1ed140216085ae5e26da4e1f6422324f5ce4819c561daebfae4d25c5a6a7e75dc70e9ba8e8f717007ee45e299a56279f9898e28f1f53d2bd92db42d22ca0105ae9563042b21944715ba2a872a50b600ea549527d0e3a53bfb1f4cf2a68ff00b3ad364d2b4f2af90b87918619d8639623824f2680385f08c301bc926fb34515cfd834ff003d5630be54df6ab912c6be8aaca500f45039c573dacf8afc4167a9de986edc99af6f2d6de18c64ef97ce82d428cf512d9393ff005d2bd822b0b381dde2b482367397648c02c7733e4fafccecdf5627a9351be91a6c8632fa7da3797379e9ba153b65dccdbc71c36e7639eb9627b9a00f1cb9bb92fb442751b631bc7e16b9b491e75ccbf6b83cf42acde87cb95f1eb1839e2bd9ed512369a38d55515c05551800045e00a8a4d1f4c984825d36cdc4858bee814ee2c183678e721dc1f50edea6ae0550490002c72703a9a005a28a2800a28a2800a28a2800a28a2800ac7d77c41068d1a4611a7bc9b886dd39663fe1fceb46f6ee2b0b29aea7388e242cdefec3dcf4af3cb9b8b8b593ce9d8ff6adc8f3657ef029e5635eebc1e7ea0579f9863161a9dfaff5f89ad1a52ab3e489b22dbc6b7f99cdddb5906fbb0f191ff8e9fe74f835cd5f439d20f11c4af6f236d5bd887ca0fbe074fc01fad6d78725926d06da4964691cefcb31c93f31ef5a13c10dcc0d0cf1a49130c3238c83451a4e74e3569cddda4f577dfbaff22270e4938f61eacaea19482a46410782296b93f0dea76d6fa9cda2c17f0de5a72f672c7287da07de88907a8ea07a73e82bacaeca5539e377a3ea485636bbe208b4758e18e26b9be9bfd55ba753ee7d07f3fcf17f51be8b4cd3a7bc98fc9126ec67193d87d49c0fc6b2fc3da63a46754d41036a5747cc72c39894f4419e460751f876a8ab2937ece9e8df5ecbfad80cc16be35bd0666bdb5b3cf48703e5fc94ff00334fb6d7756d1ae63b6f11c2a6073b52f631f2e7fdac71fa0aebaa2b8b782f2dde0b88d2585c61958641acfeab28fbd09bbf9bba7eabfc8448082010720f42296b9ed11e5d2b529b42b8919e255f36ca47392d1f74cfaaff002f418ae86b7a5539e37d9f518560ebbe223a74f1d858db9bbd4a51f2c4bc841ead8ff3c76abdadea6349d2e5b90bbe5e1218c725dcf0063bfaf1d81a8341d20e9d6c67b93e6ea371f3dc4cdc9c9fe107d074fc2b3ab294a5ece0ecfabedff05818a2c3c6d38f39b52b5818f222e3e5f6e148fd4d4d63e23bed3eee3b0f11dbac0d21c4574bf71cfbf6ff003c815d5d57bdb2b7d42d5edaea2592271c861d3dc7a1f7acfead287bd4e6efe6ee988b14561f87eeae237b9d22fe4325d5991b246eb3447eeb7bfa1fc3bd6e574d39a9c798614514558051451400514514005145140185adaff686aba6695d622e6e6e067f813a023b82c40fc2b80baba3797935d1c832c8cf827a64e715e8365ba4f10eb176e10a42b1dbc4e3a80177b0fcd85795a4fb10e5857cb6749cd45aeadfe164bf57f33d7ca52bcdbf23d5fc2cd9f0edb7b171ff008f1a778a067c25ac8f5b19ff00f45b573be11f1169763a2cb1dfea76b048b33158e5942b95daa7217392339aa1e25f89fe1a9b49d434eb4b896e669ede5843469b55095232771071cf607a57d165d4aa4f0b4ed17b23cdc5b8c6b4937d59a9ae6871695a8db6ada5588812cedd9e45b7c468db5e3c0207fb0d37f9c5764acae8aea432b0c823a114d92349a168e450c8ea5594f707b566f871a53a05ac7300b2c01a0603b6c629ff00b2d3f86afaafcbfe1ff0322b6aea351d7b4dd30e0c71e6f675cf385e13ea0b1e47b567eb76971e29d725d152fa6b4d2ac911ef4dbb6d92791c12b167180a170c7d77018ee3574afdfebbacddf9a1d0491db20feeec5cb0fcdcfe54fd279d4f5e1ff4fcbffa4d052a2afcd3eeff002d3fe08183e0bfb145a3eb23c3b1e2c9660d680927e636f1920e79ceeeb587e1cd0a21a0dbeb7e0ebd913518228d6ead5dc982e6411a975607a37cdc10700fa751db691e1ab3d0db51fecf79215bd93cc08a7e584e31f203903924f4f41d00a77863428fc3ba05be9ea55a551be6907f1c87ef1ce39f419e70056e050bdbf4d5bc3565e21b38dc496ac2e02747500ed96324f4c0dc0ffbb5d3f5ae77468e01ac78974ef254446e52729d984b0aeefcd95c9fad5ef0eccd3787ec8bab23a47e53ab750c8769fd56b14b96abf35f97fc3afb80ab3a8d4bc5d0c470d069b0f9ac01ff0096afc2823d94120d6f56568aad249a8ddc8a81a7bc70a57aed4c4633ff007c13f8d6ad14568e5dddff00cbf003c57e28f8f35f97c4c3c23e121722e6350d7125aa932b311bb6ae39002e0923d71c60e6f7c25f1f6ada8ea373e16f13f9bfda76e85e19275d92301f791c1c12c010471920367a7327c15860d48f89bc525545c6a1a9c898dbf717fd6707af264e7fdd149f11a21a67c55f026af68365ddd5d7d9256ecd1ef45e9ebb66719fa7a56e077baeafd8eef4fd5d70be44c21989381e548769cfae1b691f8d6e554d52d4df695776a31ba585917774048e0fe749a4dcb5e68f6772ec19e5811988e9b8819fd735847ddaad77d7f47fa01728a28ad8028a28a0028a28a0028a28a00c5f0fdbed835367e44fa84edf86edbffb2d7cd5ab6ada9daea73da4d215920731ca80f0194904718eff00cabe9dd118359ce01e56eee01ffbfac7fad782fc61f0fbe91e327d41230b69a9af9a85400048000e3d739c367fdbf635d194c2949af6914ddb4b9862275234fdc6d7738692edfed8d3a31037640ce323dff2a8e08e7babb8e080349713b88d14725998e00fc7350935dcfc27f0ec9ae78d6dae9a3dd69a6b0b899b2461867cb1f5dc01c7a29af7aad454e0df63821173923e96ac8d0a36864d562624817f232fb06557ff00d98d6bd67698c1af35620e47dac0ff00c851d7ca4fe38bfeb63d622f0fc48969752a7fcb7bdb890fd7cc65fe4a2b27c3baacb71e3cf18e94c9188ad25b4991867713240a0e7d879631f535b3a0218f4ad87aadc4e0ff00dfd7ae36c94699f1f7538ccc02eaba3a5cec27aba308c0ff00be518fe7450fe147d00f45a28aafa85ec1a669b757f724ac16d0bcd2103242a824fe82b50399f06ea4bac6b3e2bbd10b4662d4cd8f272088542e41f7c93ed9ef5ade1f95a487508dbfe58dfce83e85b77fecd5ccfc1cb07b4f8776d753799f69d46796f2632756666da1bf15553f8d74ba01dcfabb0e8750931f8051fcc1aca7fc48fcc06f84bcc3e18b3793efbef73ff02763fd6b87f89b69e2ad1bc4563e33f0db493c76d6c2daeed154b028199b2ca3ef29ddce395c03ee3bdf0c48b278674f65e8210bf88e0ff2ad52401924607ad1434a51f4408f13f80fe26b2ceada0b3476cd2dc9bbb389dfe670461941e012a113a73c938c038bde34be83c4df18fc1fa2584c923e973b5d5c4a8db95486572871d0810ffe3c2bce3c65a2c3aef8f6f47856d158caecd1c1060090aa65d947bed66c0ebdb9ebd8fc058f41b7bcbe32ddb0f11c81a2fb34a9b3644082761fe224804f71b7a01c9d232525746d5a84e8c9466aced7fbcf75acaf0e422db458edc748a69a31f4595c7f4ad5aa1a39cd8c847fcfd5c7fe8e7a87fc45e8ff004312fd14515a005145140051451400514514018fa1c7f67b9d5edcb8665be69303b075561fccd45e2ef0b5a78bf40974cba631b67cc8260326290670d8ee39208ee09e9d6a51b6cfc58c08555d42dc1073c97889c8c7fbac3fef9ad77758d19dd82aa8c9627000f5acf0f2715a6e9bff0081f80ad75667cc63e19eac916ad2dd5f69f02e95298ee233231908c0285176e0efc8db92339c715ef3e08f0adbf84bc37059246bf6a7024ba90725e4239e7d0741ec3dcd73fe249adaff00c4563aa68ead7e96500bad43ecb37cb2c292828a31f2b3865760b919d847715b5a0fc42d0bc47ad4da558c92fda23048322aa8900feef39e9ce080715d5531152a2b49931a718ec755593a022fd96ee757dc2e2f679339ec1ca8fd1455bd4ef574ed32e6f1b07ca8cb004fde3d87e2703f1a34ab3fb0693696842ee8a2556dbd0b6393f89cd723d6a25d97f5fa9654d0fcd4fed2825c663be976e3fbad8907fe875c7f8f82e8de3df057894c518896e9f4fb8959f6e04ab84cfb2e646aebe155b3f14dca9c817f02caa4b0c178fe56007fba53f23593f12fc3afe26f01ea36504424bb8d45c5b0db93bd0e70bee57728ff007a8a3a4797b5d7f5f203adae1fe2dea3258fc3cbe82dd9c5dea0f1d940a8b93233b7cca3ea81c56a780fc51178bbc2165aa2b86b8da22ba5c01b6650377009c03f787b30ae5f5eba5f157c5fd13c3d0b335b6859d4af76c9b47980298f8ee5494fc1dbd0d6a077da369cba3e87a7e988e5d6ceda3b70e46376c50b9fd2a0f0f18e4d31ae23185b8b89e607d4191b07f2c55ad4ef574ed32e6f1b07ca42c01fe26ec3f1381f8d3b4fb5fb169d6b6b90de4c4b1e477c0033593d6a2f25f9ff00c33029787214b6d1c5b21cac33cf18fc25703f4ae53e2beb37161a3db69f6e5905f1712ba9c1d8b8cafe3b867d811debabd3d85beb7a9d990aa6464ba8c06e5959429e3fde43ff007d0ae27e31da4e74cd375055dd6f6f23c72e01257785da7e995c7d48ac5dfeaf68f4d3eed0edcb545e2a0a5b7f56fc4e3fe145bc43e202997964b691a124ff001703f916af42f10fc33b0d57c5ba5f896c241637f6b7714f71b57e5b855604e40e8c718cf7ee0d79f7c29b596ffc791dd47b843676eeee7071f30da067d4eecf3fdd3e95ef75786bfb3d4eace9df15f2421214124e00e49accf0e46e9e1fb3691b2f2a19891eae4bff00ecd4bafca53469e2423cdb902da205b1f3c8768fcb39fc2b42189208238631b52350aa3d0018157bd5f45f9ffc31e40fa28a2b500a28a2800a28a2800a28a28032f5db79a4b38eead813736720b88d47f1e010c9d0f55247e55cdfc4bd51bfe15dcf3593929785104cbc6c43ce4fb71820fad7715c3f893474b6d16fb4bb8591b42be3cb46bb8d9b6777ddee9b867031dc77e709bf672e77b3dfcbcffcfe40733f0beda5821f15d858deadd388a2105ca9e8c636201270720b7e95cc7c347b697c7fa3a5bc0eb710adcfda64dd9524a920818f973cf739f6af55f87de15d3fc31a65c1b2d405fb5d3ab3ce3b80380793c8c9fcf1daa468b44d0b589e5d1ac639f5abd243471b93804e589ea106793f87e1a4aac231e66f4035b50737baadae9b18ca21173727d154e517a6325803d7a29f5ad6acfd274d6b08a4927904d7970dbe7980c6e3d80ff640e00ad0a54d3d652dd8193af4128b78750b640d7362fe72ae3974c61d33db2a4fe20568dbdc45756d1dc42e1e291432b7a8352d738f337856521e22fa2cb216578d79b427a82075427918e9c8e78cc4dfb2973bd9effe7fd7901e51e2bb3f10fc30f1807f095d21b4f11ca44566d12b6c9b38da01e00cc836918f420e327d0be1af82ee7c2da65d5e6ad2f9fadea7279f78fbb76d3d42673827258923a93dc006b9ff00899756f79e2df87735b4d1cd11d587cc8c08ff00590d77fa8ebf14132d95805bcd464384851b85f56723a01f9ff3ad2556118f33602df3ff00686af6fa6a13e5c056eae483c0c1f910fd4fcdf45f7ad8aa1a569c74fb77f365335d4ce649e6231bd8fb76006001ed57ea69c5eb296eff00ab018dad86b2b8b5d6503116d98ee15472d0b6327a1276901b1ec6b4ae2dedb51b3782e228ee2da65c32300caea6a574496368e4557460432b0c823d0d73915db785a44b2be677d31d88b6b9393e57a46fec3b1ff2224fd949c9fc2ff07ff0413b3ba35348d074bd06dde0d2eca3b6476dcfb392c7dc9e4d68d471cf0cd10962951e32321d5811f9d63de6be25b9fecfd1c25ddf37de60731c03fbce47f21cd692a90821ca4e4ef27763d9bfb4fc4291a9cdb69df33fa34cc0803a7f0a92783d587a56cd53d334f8f4cb14b7462ed92d248df7a473c963ee4d5ca2945a5796ec41451456801451450014514500145145001484060410083c106968a00c0b8f06687713994da98d98e4889ca83f8741f856969da458693118ec6d92207a91924fd49e4d5da2b28d0a719734629300a28a2b500a4650ca5580208c107bd2d1401cfdc782f42b89fcdfb1f96c4f22372a0fe1dbf0ad3d3b49b1d2a231d95b2440f523966fa93c9abb45651a14e2f9a314980514515a8053258a39e268e58d648d8619586411ee29f450d5c0e7a4f04e8524a5fecaca09c95595803fad6c5969f69a6c1e4d9c090c7e8a3afb93d4fe35668aca1469c1de314980514515a8051451400514514005145140051451400514514005145140051451400514514005145140051451400514514005145140051451400514514005145140051451400514514005145140051451400514514005145140051451400514514005145140051451400514514005145140051451401fffd9, 'Leyre'),
(3, 'SEFIP', 'A1234566', 'Calle Azahar, 1', 'Barcelona', 28080, 0xffd8ffe000104a46494600010200000100010000ffdb004300080606070605080707070909080a0c140d0c0b0b0c1912130f141d1a1f1e1d1a1c1c20242e2720222c231c1c2837292c30313434341f27393d38323c2e333432ffdb0043010909090c0b0c180d0d1832211c213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232ffc000110800c800c803012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00f6ba28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28aaf7b7d6ba75b35c5dccb144bdd8f53e83d4fb5095c52928abb76458aad79a859d847beeee62814f4dec067e9eb5e7bad78f6eee9cc5a6036d074f3080646fe83f9fbd71f24924d234923b3bb1cb331c927dcd74c30edeb23c3c4e794e0f968abf9f4febee3d665f1c68118f96ede43e8b0b7f5029b178ef4293efcf2c5fefc4c7f966bc9a8ad3eaf03cff00edcc4def65f77fc13dc6c356b0d4d37595dc537192aa7e61f50791572bc163924865592276474395653820fa835e83e14f19bdd4a9a7ea8e0cac7114f80377b37bfa1ef5954a0e2af13d2c16731ab250aaacdf5e9ff00ee68a28ae73db0a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a6c9224313cb2b058d14b331e800ef406c53d5b55b6d1ec1eeee5b0a38551d5dbb01f95791eb7adddeb97a67b862a83fd5c40fca83dbdfd4d4de23d7a5d7b52337296f1fcb0c64f41ea7dcf7fc3d2b1abba952e4577b9f1d99662f132e487c0bf1f30abf1e96ef10667d8c47dd2b9a6da986e2316d2aed6e7638eb5aa0bedf9f60e3920d7cee719b62284952a5ee493d76775d1af5d6fd53563e9b87387f078a83af5ff791695ad75697da4edd5696e8d3b98d1d9bbc2d236460ed5503258e71815d9a780228f417b8bbba78ef44664238d89819da7d7dce69fe0dd2d2feeffb49d316d6adb2dd48e19f1cb63db8c7bfd2acfc40d64dbda47a5c2c5649c6f908fee7231f891fa7bd7ab84a989a8af5746ddedd976fd5fdc79f89c1e028537382e68c5593fe67d5fa5f45e4afadcf37a2b7fc31e1b975ebb2cfba3b388fef241d58ff00747bff002fcaba9f19da68fa5f87841159411dc4855612b18dc304127775e9c67debd07512928f53c2a581a93a12aedda2bbf5f4fc8d7f096b0face888f31cdc427ca9093cb600c37e23f5cd6f579b7c38b875d56eedff008248779faab003ff004235e935c5563cb3691f5396d775b0d194b75a7dc145145667785145140051451400514514005145140051451400514514005715f10b57f22ce2d2e33f3cf8925f640781f891fa7bd76b5e35e29bd7bef125ec8c7e5490c48339002f1fd33f8d6f423795fb1e4e738874b0fcab7969f2ea5ff02d95adf6b92c577024c8b6eccaae3201dca33fa9a9fc67a1c1a25f4175670a8b79c10626195561fe39e9ec7b553f04dc791e29b65ce04aad19f7e323f502baff008869bbc388719db70a73e9c30feb5a545cd53925b33cdc2c62f012ab1569c1dd3ebd19c059dd42d32a9b78d1cf4650073572e18cac9670e1a79d846abee78e7f3acab08bccba4247ca9f313e98e9fae2badf06e8efa86b275795316d0b1f2f3fc6e06071e83afd715e054cb30ef31f76fcb049b57beb7d16bdf7b1f4384ce3192ca791db9aa49c53b24f96def4b44969b5ff00c8ee2c6d6df43d1638770586da22cee7be0659bf3c9af2b315ef8b3c473340a59e772d96e91a0e0648ec06057a6f89acaeb51f0fdd5ad9e0cd26dc027190181233f8553f0d6911f86f43792f5a3499ff007b3b923083b2e7dbf9935edd39a8c5cbab3cdc661655eac285ad4e2aedfe162cdc4f63e11f0f2800f950aed8d4b7cd239c9ebee727db9af320352f166b7d7cc9e5f5e1235fe8a3fce49ad1d7351baf17eb71dad846cf0c795897a67d5cfa7f8577da0787edb41b311c603dc301e6cbdd8ff855dd528ddfc4ce694259855f670d2943f1f4feb4465f87fc20fa06adf6a5bb13a342d1b029b48248231c9c8e0d7574515cf29393bb3dca187a7423c94d59051451526c145145001451450014514500145145001451450014514500437530b6b39a73d238d9cfe0335e14cc598b31c92724d7bade43f69b2b88338f36364cfd462bc2482a482304704575e1ad667cd67fcdcd0edafe9ff00d1f0fff00c8c7a6ff00d7cc7ffa10af59d7b4c3ac68973628cab24801466e8181047f2af2ef092a3f8ab4f0f8c7984f3ea0123f5c57b1d4e21da49a35c92929e1ea296cddbf03cbf4ef006ab3cbfe96d1dac59c37cdb988f6038fcc8af4ab4b486c6d22b5b7409144bb547f9ef535158cea39ee7a985c0d2c2af737eec2b27c43a2ff006f69cb69f68306240fb82eece0118c647afe95ad59bae19c69e3c9f336f98be7795f7bcbfe2c62b1a955d183a8ba1d8b0f1c43f632da5a0ed2346b4d12d05bda275fbd236373fd4d68561269fa6ca824d1e6863ba4c32324a4f7e8c33d0fbd49aaadb4baa5a457ae16dfcb76c349b06ecae39c8ae7962a7cae724ba75d3576dedd3d0e9a783a716a9c1b495f4b59ab2ed7ebea6cd15956767a30b956b478da65e46db82c7f2cd5348b4cb9bbbd7d4254f345c32aef9cafca00c71914a5899a4b48ddbfe6d3efb7e852c3c5b7abb2feeebf75ff0053a1a2b0afa0b283c3d7df612a6365f98ac85c678ef9353ea912dcdfe9d6d2e4c32172ea188c90bc74a1e2a4aeacafeef5d3de76dedfa0961d3b3bbb6bd35d15f6bfea6b628ac3bbb4b6d2e7b47b2678a69275431890b798a4fcc0824f41dfb56e56d4aaca6dc64acd76775fa7e467569a8a528bba7e56ff0030a28a2b6310a28a2800a28a2800a28a2800a28a2800af1df15e9efa7788aed187c93399a33eaac49fd0e47e15ec5581e2af0eaebb603ca0a2f22e6262700faa9ff3d6b6a33e596a7999ae11e2287bbf12d51e59a5dcad9ead6774f9d914c8ed8eb804135ee2ac194329041e411debc1e6865b799a19a378e45386471823f0af47f026be97564ba55c4805c403f75b8f2e9e9f51fcb1e86b6c442eb991e4e498954ea3a33d39b6f5ec76545145719f52154f52bb92c608e7540d1090098e09da9dcf1e9c55ca8ae2e60b58fccb89a385338dd230519f4e6a67194a2d45d99519c60f9a7b18daa4ba4cb6aef0c9035e37fa96808f30bff0fdde7ad3ef8dbaea760da888b6981c379a015ddf2faf15ab6e2da4413db792cae32248f0437e23ad3e478e300c8caa090a0b1c724e00fceb91e1252bb6d5ddba69a3bebaea75471718a56bb5af5eeba6850b69f4659d45b3d92cadf28f2f6827db8a8ac2cedae26bf79ada19185d380ce809c607ad6b600ec29914914a9e642e8e8c4fcc84104f43c8abfab5dae6b59797fc399bc425751bddf9f633b58822b7d02ed21892352b9da8a00ce47a53753862b8d574b8a68d644264cab0c83f2d6a168dd9a225598282c8793839c647a1c1fcaa082fec6ee5d96f756f348a376124562074cf1454c229b7dbddd2dd9dff0010862d4124debef75eeadf80b069d676d279905ac31bff00795003f9d59a29934d15bc4d2cf2a451af57760a07e26ba2108c55a2ac6139b7ef4dfde3e8a6a3a491ac91b2ba3005594e4107b83491cb1cc81e27575248dca72320e0fea2a89ba1f4536491224dd23aa2e40cb1c0c93803f334ea0770a28a2800a28a2800a28a2800a28a2803235af0de9fae283731959946d5990e180f4f423eb5ca7fc209aae99791dde97a844d24672a5c1420fa6390457a1515a46aca2ac7157cbe856973c95a5dd68ccbb2bed4dc2a5f692f1be40324532327d792081ed835a945150ddfa1d54e0e0ace4dfadbf4482a86ab1492430b450c923c728706270ae9f29195dc7693ce3078c1357eb2f5b9ae628ad12d4ca1a5b808c21d9b88d8c782fc7614e1b918869536dfe1b99d158ea68f09920989c83198e658c447cd62cd22a901895209c020904719a9a2b4bf031e45c2cfe7219666b8cac9895492abb8e06d078e3038c1ed149a95fd93cb12ef791523245d1525411213c46393f281c678e71c1a626bd7663b892048a4086498f9b27023448c955217befe09ad9a933cc53a11d1b976fd3b6fff000c4367a76af1db81769732c5b90cb0acfb5dced70c43799d3250f55ce3a0ef5adb49f10446c9512584c4c3244c18052cc5b77cf83d7b29ce3af6ad06d76fe396f23090bb473ca57706c08d150e38e84ef1c9e073525e6bb7b02cad1c30100cac808763b231f31381819240cf007bf4a779f65a997b3c2ff34bddfeb7f9226d0acee6da79e4b8b79a367821466966f30bc8bbf791f31c0f987a7d2a9c763aa2e8bf6131df6f48d13892109c30ced20863c678246464641c52cbe20bc782ea511c51c01a6850ab7ef159623206e411db18c7bf35a365aa4d73ab4b6ed1a083f7a11806ce6365539278392c7a74c75a97ccb5b1d10fabce31a7193eabd6ff0023320b4d48dcc2b2dbdd891121dae2e0ec8ff78e5b3973b895c647cde99ef4db6d2f529a79e2bd8a7fb3ced1b38f3be50431dc07ce4f208e78ce3a0ab93ebf3c57172ab6e8d1a09445bb2a774639ce7a8273d3a719ebc4cda8ddff0062ea92c8228eeacc48bba2c95242060403f51d7d28bcbb131861dbb73376bfe5e9ae85186c3548d60478e77d802c2cb718106246e5c6ef9c6cdbebd31c66a0b7d3b598750b56f2655862989f9251828d2396c8dc3b11d8e7d4631571efb55b152ad1c8fe6be21fb5ed2dc2b3367cae31c003bf527814c9b5abd9da392158a1b5276b61f74858db99076db8e47e5e869de4fb10e3415aee49ab2b75fcbc93f4b7a136af657973a9232413cb1e6028c930548f6c997dcbb8678c763fa53740b3d5edaf677d424731b261b73e43c9b8fccbf31c0c7b2f51c7155ffe120bc7b09de0f2c88ed9cacac096f31503124e029eac300751f855db8d4af200f34be4ac71171b509f9cac658e4907033e833c7e149a95b94d14a84aa7b64de9f76bfd7e86e5159da45f4f7b15c0b844596098c44a020370181c1e470c3835a358b567667a94ea2a91528ecc28a28a45851451400514514005145140051451400535a3472a59558a1dca48e8718c8fc09a75140104d656b739f3eda1972413e6206ce338ebe993f9d24963692e3ccb585f0fbc6e8c1f9ba67ebc0fcaac514eec9708bdd15e5b0b39f779b6b049b9839df183960300fd714b358da5ca859ed6195431601e30c013d4f3dea7a28bb13a707ba2941a469d6c64315940a640439d83907a8fa7b74a956c6d16e4dcadac027273e688c6ef4ebd6ac5145d8952a6b4515f710fd92dbce926fb3c5e6c8bb5df60cb0f427b8a7182229229890acb9f306d187c8c73ebc7152514ae572c7b11cf6f0dd47e5dc431ca99ced91430cfd0d31acad5ee05c3db42d301b4486305b1d319eb8a9e8a7760e117ab455fecdb132994d9db9908da5bca5ce318c671e9c7d2a5fb2dbf9422f223f2c0dbb360c6318c63e9c54b45176254e0b64416f676b681becd6d0c3bb1bbcb40b9c74ce2a7a28a45462a2ac905145140c28a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a00fffd9, 'Manuela'),
(4, 'Logomed', 'A1234569', 'Calle Manuel Machado, 34', 'Sevilla', 41005, 0xffd8ffe000104a46494600010200000100010000ffdb004300080606070605080707070909080a0c140d0c0b0b0c1912130f141d1a1f1e1d1a1c1c20242e2720222c231c1c2837292c30313434341f27393d38323c2e333432ffdb0043010909090c0b0c180d0d1832211c213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232ffc000110800c800c803012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00f7fa28a2800a28a2800a28a2800a28a2800a0900124e00ef4d7758e3691d82a282589e800ae0f5cf1149a9830400c56b9e413cbfd7dbdaaa316cc2bd78d1577b9b57de30b6b798c76b09b8c705f76d5cfb707358373e28d52e09db32c2bfdd8d71fa9e6b1a8ad94123c99e2aacfad8b8756d449cfdbeebfefeb7f8d5983c47aadb918ba671e9200d9fcf9acaa29d9192ab35aa6cec2cfc66aee89796c101e1a48db207bedffebd7575e495e83a5ea27fb02d6f2662428f2e4e7a007683f5e9f9d6538a5aa3d2c1e22551b8cd9b545145667a014514500145145001451450014514500145145001451450014514500145148cc154b31c00324fa500725e2dd5ceefecd84b0c61a561c678c85fd735c954d7770d777935c30c195cb6339c64f4a86ba22ac8f9faf55d49b930a28a2a8c828a28a002bb2f0bc4b77e1fbab563d653f865460fe62b8daebbc12e7fd353b7c847eb513f84eac13b5646c697a819b4e85d86023085c93924f001fcc8fceb56b97d2e3cc3a9d9f25b69283dc679fcf15d0585d7db6ca2b8dbb4b8391ee0e3fa5607b499628a28a061451450014514500145145001451450014514500145145001599e2199a0d06edd7a950bf83100ff003ad3ac8f13ff00c8bb75ff0000ff00d0c538ee6759da9cbd19e7745145749f3c145145001451450015d778250e2f64edf228fd6b91aee3c1e047a34f2b0c0331393e8147ff005ea27f09d58257ac88f457cebf391d183ff3cd5ff0dbb3698c09e165207d300ff5acff000d445af26989e1131f893ffd6357fc33ff0020d93febb1fe42b03d946cd1451414145145001451450014514500145145001451450014514500155efed45ed84f6c4e3cc42a0fa1ec7f3ab14502693566792515a9afd8358eaf30dbfbb918c919c6060f38fc3a565d74a773e767170938be814514532428a28a002bb908da67842389fe59645c74fef1248fcb22b90d3ad0df6a305b0ce24701b1d8753fa66bafd72492fb50874e879c727ea47f41fceb2a8fa1e8e021f14fe4334c5fb26837974d95320dabfc81fcc9ad0f0f45e5e94ad9cf98ecdf4edfd2aaeabbb167a3c04fcc1431c761c0fe449fa56e4512431244830880002b23d2487d145141414514500145145001451450014514500145145001451450014514500656bda38d5ad155582cd112c848ebc74fc78e7dabcedd1a3728ea5594e0a91820d7acd61eb5e1c8b5493cf8a410dc63049190ff005f7f7ad213b68ce1c5e17da7bf0dce028a9eeece7b19da1b88d91c7af43f43dea0ad8f21a69d98514558b1b39350bd8ed62c6f73d4f403a9340d26dd91bfe18b68edad2e7579973e5e522078e71cfe7903f3ad3b14fb1c12eb17a4b4b203b508e793fe7f0ab22d239a28ad50b2d95a615b8ff5cc3b63ebf993568da3ddcf1cd75811a7ddb7ea01f527b9ae693bbb9eed1a5ece0a256d2ec6433bea3743f7d2f28bfdc07ffadc7b56bd14523741451450014514500145145001451450014514500145145001451450014514500145145004373696f791797730a4a9e8c3a7d3d2b96d6fc3565656135e4324a9b00f90fcc39207d7bd75f585e2d9fcad0d9319f36454fa7f17fecb5516ee73e261074dca4b5b1c0d777e1bd10d85b9b99c32dccab8c7f717d3ebc03593e19d07ed4e2fae811123031a11f7c8eff4fe75db55ce5d11cb82c3dbf792f908aaa8815400a06001da968a2b23d20a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800acfd534c5d556086572b023ef703ab1c600f6ea6b428a13b1328a92b31a88b1a2a22855518000e00a7514505051451400514514005145140051451400514514005145140051451400550d4b50934f55905b79911e0b07c60fd3157eb375eff903cdf55ffd085027b17e1944f0472a8203a8600fb8cd3999514b310140c927b0a834ff00f906daff00d714fe42aa6bf39834a700906421323f33fa03405f41d6fa8cf78646b6b50d0a9c2c8f26ddff00418352586a0b79bd1a330cf19c3c4c7247bd3b4b8d62d2ed957a18c37e2793fceb32e1c5af8aa1704e27401c7ae7207f21401b534c96f0bcb21c220c9354ad2feeaf61f3a2b34119276979b04ffe3b54fc4d237916f001feb1c9cfd3ff00d75b71c6b146b1a0c2a8c003d280ea4167762e95c1431cd19db2464e4a9fea3de9d79771595b34f2e7038007527d0511db08af27b807fd705c8f7191fe1591ac6ebbd5ad2c49fdd1c3b01df93fd07eb40742e3dfde2598b96b01b31b8a89be603d718abb6f711dd409344728dd0e2a4650ca5580208c107bd60f872728f7164c39462e0fe87fa500685c6a6b6faa4366d1f128077eee849200c63daafd73daec2ff6a6bb8df6b5ba467f366fea056d4775149662e81fdd94de4fa0a0132947acacbab9b158780c57ccdddc024f18f6a9353d464d39564fb30922240dde663079e318f6ac4d3d193c4501724bc8a646c8c72c849fe75a9e23ff00905ffdb41fd6815dd87aea57ed009974b2c8cbb81138248fa63356a3bb6b8b05b9821deec32232db79ce08cfe74ed3ff00e41b6bff005c53f90a92188428541c82ccdf9927fad03326d75cb8bc2de469c5f6637626031f98f6ab967aa45753340c8f0dc2f58dff00a567786ffd75f7fbcbff00b352f886236ef05fc2764a1b6961d4f1c7f2340aeed737a8a643289a08e50301d4301f519a7d0505145140051451400566ebdff2079beabffa10ad2acfd6a37974a952346762570aa327a8a04f62c69fff0020db5ffae29fc8567f89119b4c52070b2827e9823fad3ad751682d2189b4fbe2c91aa9c43c640c7ad59755d574f96368e6803f189570411c838f4a03744ba7ff00c836d7feb8a7f2158fa9c665f12d92af5daa7f2627fa54d69a80d3a1fb25f2ba3c5f2a3ed24483b638a758db4b77a8b6a73a34431b628dba818c67f9fe740b720f1292af652638566cfe9fe15bf54f53b11a85a18776d607729ed9f7fceab45ac240823d411e19d783f2921bdc6281ecc8ed6f66bcd76780c8c90c39c22e392081cfeb515e911f8a6d189e1900fcf70a768f6d31d4aeaf5a364865ddb370c1396cf4ab3ac69ef771a4d07fc7cc272bee3d2817434eb9bd0019756bb9d7ee608fcdb23f955e6d711a1db14129bb3c79254e54fbfb54fa458369f67b1c8323b6e6c76f6a07bb09a0173717901c0df6e832467072f835916329bad1bfb381c48d308ffdd539627f46ade4561a84cc54ed314601c704e5ff00c4567c1a718bc452dc6c222d9bd48e9b8f07fafe7409958ffc8e2bf4ff00d92adf88ff00e417ff006d07f5a82e5041e29b699ce1255e09e99c118fe5f9d59d7a2926d376451bbb6f070a32680e8c8ecf48b796ca0919e705a356389081c8ad7036a803b0c564dbea4f0594311d3ef4bc71aaff00aae090315a166d2c96e24990a48e4b1427ee8ec3f2c503463f86ff00d75f7fbcbfcdaa4f13ba8b18933f3197207b007fc4556d2e49f4f9ae8c96376e24236ec889e99ff1ab2d6771abdda4b790982da2fbb193f337d7d3a7f9eb40ba58d5b3468aca08d861963553f502a6a28a0a0a28a2800a28a2800a28a2800a28a2800a2aa437924f79750a42365bba21767c6e25431c0c760cbf5cf6a82fafd934a9275565267107cadc8cca23dc0e3df3401a545642eb87edc96ad68ca5ee4c0ac5b865c39dc38f58c823b6454965abfdb648234876bc91c921cb642857dbe9dffa50069d15564bd48f508ad494cc8bddb9cf25463dc2bffdf3ef53bbec68d719dedb7e9c13fd2801f4565ff6c1c1905bfee05efd9376fe7aecdd8c7f7f8c7a73ed562d2fe3bb19428019244552df3108c549c7d41ff3c5005ca2b2ceae63861796df6b4d6af72aa1f38da1495271fed0e7eb4f1aac0d1c7379b1ac2f2ec49376564e3b1c7ae47e14017de34906244571d70c334eaa10ea2f34d328854470dcf90ec64e7ee820818f560319f7a8ecb57fb6c904690ed7923924396c850afb7d3bff004a00d3a2a9cf7ad1ea56d6891071267cc6df831f0c54e3b83b187e5572800a28a2800a28a2800a28a2800a28a2800a28a2800a28a28028b69ec2fdaea0b9921129569a3500890af03a8e38e0e3b01d3151c9a534d14b0bdd3185e65995360f9089049d7be48ad2a28033e4d22096e2ce7627ccb59a4951b1fdfdd95fa723f2151a68fe44300b6b968a68b7a897606255db715c1e3ae3f2fad6a514019936876d3dfb5eb926e7cd8e449303746171f203e87e6cff00bc6adde5bcb708821b8303a3eedc1037623183f5ab14500669d1e2379e6f98fe479df68f23f87cdfef67afbe3a679a7c5a6086fbce59bf72199d21d83e566fbdf375c1249c7a9fa55fa28032e2d1952368e4b86915616b7832a3314671919ee785e4ff0077eb9bb7504b304f26e0c2cad92c103647a7353d14019d0e96d0dfcb71f6b94c524a66300550bb8a85e4e327a67eb4c4d1fc886016d72d14d16f512ec0c4abb6e2b83c75c7e5f5ad4a28028dce956d7574b74e8a2e50c7b26da372aab6eda0f607241f6357a8a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28acad175b4d604db61313444705b3907a1fd29d89738a6a2f766ad154353d4974d8a3631f985db00671c55f072011de91414560697e288b52f105f693f6668ded8b0572d90fb5b69e31c7eb5bf40051451400514514005145140051451400514514005145140051451400514514005145140051451400555b2d3ad74f5905ac4231236e6e73fe4514502693771f776705ec6a93a6e556dc39c54f45140ca169a358596a1737d6f004b8b9e646c939e7271e993c9abf4514005145140051451400514514005145140051451401ffd9, 'Adriana');

INSERT INTO `PROVEEDOR_SERVICIO` (`ID`, `PROVEEDOR`, `SERVICIO`, `PRECIO`, `DESCRIPCION`) VALUES
(1, 2, 2, 25, 'Traumatología pediátrica'),
(2, 2, 12, 20, 'Nariz, boca y oídos'),
(3, 3, 13, 32, 'for a baby with weak muscles'),
(4, 4, 10, 18, 'Logopedia pediátrica'),
(5, 4, 14, 40, 'Dentista pediátrico');

INSERT INTO `CLIENTE_SUBSCRIPCION` (`ID`, `CLIENTE`, `SUBSCRIPCION`, `FECHA_INICIO`, `FECHA_FIN`) VALUES
(1, 5, 1, '2021-12-11', '2022-12-11'),
(2, 6, 2, '2021-12-11', '2022-01-10');

INSERT INTO `SOLICITUD` (`ID`, `CLIENTE`, `PACIENTE`, `PROVEEDOR`, `SERVICIO`, `FECHA`, `ESTADO`, `NOTAS`) VALUES
(1, 5, 1, 4, 5, '2022-01-12', 'ACEPTADA', 'Bebé 2 meses'),
(2, 6, 4, 2, 1, '2021-12-22', 'ACEPTADA', 'Por favor confirmar'),
(3, 6, 3, 3, 3, '2021-12-23', 'RECHAZADA', 'Por favor confirmar');


INSERT INTO `MENSAJE` (`ID`, `CLIENTE`, `PROVEEDOR`, `ORIGEN`, `MENSAJE`) VALUES
(1, 5, 4, 5, 'Hola, estaría interesada en una cita con vosotros, pero mi hija solo tiene dos meses, ¿es pronto para una consulta?'),
(2, 6, 3, 6, 'Estoy interesada en una consulta privada con vosotros'),
(3, 6, 3, 3, 'Hola Mary, puedes pedirnos cita a través de Baby+ cuando quieras'),
(4, 5, 4, 4, 'Hola, para nada!');

INSERT INTO `CITA` (`ID`, `SOLICITUD`, `FECHA`, `ESTADO`, `NOTAS`) VALUES
(1, 2, '2021-12-22', 'FINALIZADA', 'Realizada antes'),
(2, 1, '2022-01-12', 'FINALIZADA', '');

INSERT INTO `VALORACION` (`CITA`, `CHUPETES`, `MENSAJE`) VALUES
(1, 4, 'Nice'),
(2, 3, 'Trato correcto');

INSERT INTO `POST` (`ID`, `AMBITO`, `FECHA_EXPIRACION`, `POST`) VALUES
(1, 'CLIENTE', '2021-12-13', 'Leyre Martí se ha unido a nosotros!'),
(2, 'CLIENTE', '2021-12-13', 'SEFIP se ha unido a nosotros!'),
(3, 'CLIENTE', '2021-12-13', 'Logomed se ha unido a nosotros!'),
(4, 'CLIENTE', '2021-12-16', '5% de descuento en todos los servicios'),
(5, 'PROVEEDOR', '2021-12-16', 'Clientes nuevos con un 5% de descuento'),
(6, 'PROVEEDOR', NULL, 'Habla con los clientes a través de mensajes'),
(7, 'CLIENTE', NULL, 'Habla con el profesional antes de tu cita');
